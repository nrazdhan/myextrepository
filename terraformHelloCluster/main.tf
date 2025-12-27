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

data "aws_vpc" "myVpc1" {
  filter {
    name   = "tag:Name"
    values = ["myVpc1"]
  }
}

data "aws_internet_gateway" "existing_gateway" {
  filter {
    name   = "attachment.vpc-id"
    values = [data.aws_vpc.myVpc1.id]
  }
}

resource "aws_security_group" "myHelloSecurityGroup" {
  vpc_id = data.aws_vpc.myVpc1.id

  ingress {
    from_port   = 8081
    to_port     = 8081
    cidr_blocks = ["0.0.0.0/0"]
    protocol    = "tcp"
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
    protocol    = -1
  }

  tags = {
    Name = "mySecurityGroup"
  }
}

resource "aws_subnet" "mySubnetA" {
  vpc_id                  = data.aws_vpc.myVpc1.id
  cidr_block              = "172.0.2.0/24"
  availability_zone       = "us-east-2a"
  map_public_ip_on_launch = true


  tags = {
    Name = "mySubnetA"
  }
}

resource "aws_route_table" "myRouteTable1" {
  vpc_id = data.aws_vpc.myVpc1.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = data.aws_internet_gateway.existing_gateway.id
  }

  tags = {
    Name = "myRouteTable1"
  }
}

resource "aws_route_table_association" "myAssociation1" {
  route_table_id = aws_route_table.myRouteTable1.id
  subnet_id      = aws_subnet.mySubnetA.id
}

data "aws_ami" "myHelloServiceAmi" {
  most_recent = true
  name_regex  = "myHelloServiceAMI2"
  owners      = ["182643220141"]
}

data "template_file" "app_env_template" {
  template = file("app.env")
  vars = {
    JDBC_URL = "jdbc:mysql://Naveen Razdhan:3306/naveen"
  }
}

/*
resource "aws_network_interface" "myNetworkInterface1" {
  subnet_id       = aws_subnet.mySubnetA.id
  security_groups = [aws_security_group.myHelloSecurityGroup.id]
  private_ips     = ["172.0.2.100"]

  tags = {
    Name = "myNetworkInterface1"
  }
}
*/

resource "aws_instance" "myInstanceA" {
  ami                         = data.aws_ami.myHelloServiceAmi.id
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.mySubnetA.id
  security_groups             = [aws_security_group.myHelloSecurityGroup.id]
  associate_public_ip_address = true

  /*
  network_interface {
    network_interface_id = aws_network_interface.myNetworkInterface1.id
    device_index         = 0
  }
*/
  tags = {
    Name = "myInstanceA"
  }

  provisioner "file" {
    content     = data.template_file.app_env_template.rendered
    destination = "/home/ec2-user/HelloWorld/app.env"

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("~/PEMkey/nrazdhan2keypair.pem")
      host        = self.public_ip
    }
  }
}



resource "aws_subnet" "mySubnetB" {
  vpc_id                  = data.aws_vpc.myVpc1.id
  availability_zone       = "us-east-2b"
  cidr_block              = "172.0.3.0/24"
  map_public_ip_on_launch = true

  tags = {
    Name = "mySubnetB"
  }
}

resource "aws_route_table" "myRouteTable2" {
  vpc_id = data.aws_vpc.myVpc1.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = data.aws_internet_gateway.existing_gateway.id
  }

  tags = {
    Name = "myRouteTable2"
  }
}

resource "aws_route_table_association" "myAssociation2" {
  subnet_id      = aws_subnet.mySubnetB.id
  route_table_id = aws_route_table.myRouteTable2.id
}

resource "aws_instance" "myInstanceB" {
  ami                         = data.aws_ami.myHelloServiceAmi.id
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.mySubnetB.id
  security_groups             = [aws_security_group.myHelloSecurityGroup.id]
  associate_public_ip_address = true

  tags = {
    Name = "myInstanceB"
  }

  provisioner "file" {
    content     = data.template_file.app_env_template.rendered
    destination = "/home/ec2-user/HelloWorld/app.env"

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("~/PEMkey/nrazdhan2keypair.pem")
      host        = self.public_ip
    }
  }
}

resource "aws_subnet" "mySubnetC" {
  vpc_id                  = data.aws_vpc.myVpc1.id
  availability_zone       = "us-east-2c"
  cidr_block              = "172.0.4.0/24"
  map_public_ip_on_launch = true

  tags = {
    Name = "mySubnetC"
  }
}

resource "aws_route_table" "myRouteTable3" {
  vpc_id = data.aws_vpc.myVpc1.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = data.aws_internet_gateway.existing_gateway.id
  }

  tags = {
    Name = "myRouteTable3"
  }
}

resource "aws_route_table_association" "myAssociation3" {
  route_table_id = aws_route_table.myRouteTable3.id
  subnet_id      = aws_subnet.mySubnetC.id
}

resource "aws_instance" "myInstanceC" {
  ami                         = data.aws_ami.myHelloServiceAmi.id
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.mySubnetC.id
  security_groups             = [aws_security_group.myHelloSecurityGroup.id]
  associate_public_ip_address = true

  tags = {
    Name = "myInstanceC"
  }

  provisioner "file" {
    content     = data.template_file.app_env_template.rendered
    destination = "/home/ec2-user/HelloWorld/app.env"

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = file("~/PEMkey/nrazdhan2keypair.pem")
      host        = self.public_ip
    }
  }
}

data "aws_ami" "myNginxAmi" {
  most_recent = true
  name_regex  = "myReverseProxyAMI"
  owners      = ["182643220141"]
}

resource "aws_subnet" "myNginxSubnet" {
  vpc_id                   = data.aws_vpc.myVpc1.id
  availability_zone        = "us-east-2a"
  cidr_block               = "172.0.5.0/24"
  map_public_ip_on_launch = true

  tags = {
    Name = "myNginxSubnet"
  }
}

resource "aws_route_table" "myNginxRouteTable" {
  vpc_id = data.aws_vpc.myVpc1.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = data.aws_internet_gateway.existing_gateway.id
  }

  tags = {
    Name = "myNginxRouteTable"
  }
}

resource "aws_route_table_association" "myNginxRouteAssociation" {
  subnet_id      = aws_subnet.myNginxSubnet.id
  route_table_id = aws_route_table.myNginxRouteTable.id
}

resource "aws_security_group" "myNginxSecuritygroup" {
    vpc_id = data.aws_vpc.myVpc1.id

    ingress {
        from_port=22
        to_port=22
        protocol="tcp"
        cidr_blocks=["0.0.0.0/0"]
    }

    ingress{
        from_port=80
        to_port=80
        protocol="tcp"
        cidr_blocks=["0.0.0.0/0"]
    }


    egress {
        from_port=0
        to_port=0
        protocol=-1
        cidr_blocks=["0.0.0.0/0"]
    }

    tags = {
        Name = "myNginxSecuritygroup"
    }
}

resource "aws_instance" "myNginXInstance" {
  ami                         = data.aws_ami.myNginxAmi.id
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.myNginxSubnet.id
  security_groups             = [aws_security_group.myNginxSecuritygroup.id]
  associate_public_ip_address = true

  tags = {
    Name = "myNginXInstance"
  }
}




