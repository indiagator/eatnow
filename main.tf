terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.57.0"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "ap-south-1"
}

variable "existing_security_group_id" {
  description = "The ID of the existing security group"
  type        = string
}

resource "aws_instance" "app_server" {
  ami           = "ami-0c2af51e265bd5e0e"
  instance_type = "t2.micro"
  key_name      = "lone1connect"
  vpc_security_group_ids = [var.existing_security_group_id]

  tags = {
    Name = "cloud-lone1-test-instance"
  }
}

# DocumentDB Cluster
resource "aws_docdb_cluster" "mongodb_cluster" {
  cluster_identifier      = "restored-db-cluster"
  engine                 = "docdb"
  engine_version         = "4.0.0"
  master_username        = "indiagator"  # Keeping your existing username
  master_password        = "indiagator"  # Keeping your existing password
  db_subnet_group_name   = aws_db_subnet_group.private_subnets.name
  vpc_security_group_ids = [aws_security_group.allow_mongodb.id]
  skip_final_snapshot    = false
  final_snapshot_identifier = "docdb-final-snapshot-${replace(timestamp(), ":", "-")}"

  lifecycle {
    create_before_destroy = true
  }
}

# DocumentDB Instance
resource "aws_docdb_cluster_instance" "mongodb_instance" {
  identifier         = "restored-db-instance"
  cluster_identifier = aws_docdb_cluster.mongodb_cluster.id
  instance_class     = "db.t3.medium"  # Smallest available instance class for DocumentDB
}

resource "aws_db_subnet_group" "private_subnets" {
  name       = "main_private_subnets"
  subnet_ids = ["subnet-53f60928", "subnet-5937e014", "subnet-8f6efee6"]

  tags = {
    Name = "My DB subnet group"
  }
}

resource "aws_security_group" "allow_mongodb" {
  name        = "allow_mongodb"
  description = "Allow inbound MongoDB traffic"
  vpc_id      = "vpc-7f0e9816"

  ingress {
    from_port   = 27017  # MongoDB default port
    to_port     = 27017
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
