resource "aws_vpc" "myVpc2" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "myVpc2Name"
  }
}

resource "aws_internet_gateway" "myIgw1" {
  vpc_id = aws_vpc.myVpc2.id

  tags = {
    Name = "myIgw1Name"
  }
}

resource "aws_route_table" "myRouteTable1" {
  vpc_id = aws_vpc.myVpc2.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.myIgw1.id
  }

  tags = {
    Name = "myRouteTable1Name"
  }
}

resource "aws_subnet" "mySubnet1" {
  vpc_id            = aws_vpc.myVpc2.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "us-east-2a"

  tags = {
    Name = "mySubnet1Name"
  }
}

resource "aws_route_table_association" "myAssociation1" {
  subnet_id      = aws_subnet.mySubnet1.id
  route_table_id = aws_route_table.myRouteTable1.id
}

resource "aws_security_group" "mySg1" {
  name        = "mySq1"
  description = "allows ports 80 and 22"

  vpc_id = aws_vpc.myVpc2.id
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
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
    protocol    = -1
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "mySg1Name"
  }
}

data "aws_ami" "myAmi1" {
  most_recent = true
  name_regex  = "al2023-ami-2023.9.20251208.0-kernel-6.1-x86_64"
  owners      = ["137112412989"]
}

resource "aws_instance" "myInstance1" {
  subnet_id              = aws_subnet.mySubnet1.id
  vpc_security_group_ids = [aws_security_group.mySg1.id]
  ami                    = data.aws_ami.myAmi1.id
  instance_type          = "t2.micro"

  tags = {
    Name = "myInstance1Name"
  }
}
