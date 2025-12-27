terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.6.0"
    }
  }

  required_version = ">=1.0.0"
}

provider "aws" {
  region = "us-east-2"
}

data "aws_ami" "myBaseAmi" {
  most_recent = true
  name_regex  = "mySqlAMI"
  // "al2023-ami-2023.9.20251208.0-kernel-6.1-x86_64"
  owners = ["182643220141"]
  //["137112412989"]
}

resource "aws_instance" "mySqlInstance" {
  ami           = data.aws_ami.myBaseAmi.id
  instance_type = "t2.micro"
  key_name      = "nrazdhan2keypair"

  tags = {
    Name = "mySqlInstance"
  }
}

/*
ssh -i "nrazdhan2keypair.pem" ec2-user@ec2-3-22-242-79.us-east-2.compute.amazonaws.com
https://aws.amazon.com/linux/amazon-linux-2023
sudo dnf update -y
sudo dnf install https://dev.mysql.com/get/mysql84-community-release-el9-1.noarch.rpm -y
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2023
sudo dnf install -y mysql-community-server
sudo systemctl start mysqld.service 
sudo systemctl enable mysqld.service
sudo grep "temporary password" /var/log/mysqld.log 
mysql -u root -p
alter user root@'localhost' identified by 'Danzar12%#1';
create user frodo@'%' identified by 'Danzar12%#1';
grant all privileges on *.* to frodo;
mysql -u frodo -p
show databases;
create database naveen;
use naveen;
create table chars (
    -> id integer primary key auto_increment,
    -> name varchar(255) not null
    -> );
insert into chars (name) value ("Alexdander The great"), ("George Washington"), ("Frnaklin Rooselvet");
select * from chars;
+----+----------------------+
| id | name                 |
+----+----------------------+
|  1 | Alexdander The great |
|  2 | George Washington    |
|  3 | Frnaklin Rooselvet   |
+----+----------------------+
*/