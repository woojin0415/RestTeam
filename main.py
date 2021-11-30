import socket


host = '192.168.0.160'  # Symbolic name meaning all available interfaces
port = 9999  # Arbitrary non-privileged port
num = 0
server_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_sock.bind((host, port))

server_sock.listen(1)

client_sock, addr = server_sock.accept()

data = client_sock.recv(4096).decode()




client_sock.close()


server_sock.close()