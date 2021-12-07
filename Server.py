import socket


host = '192.168.0.160'  # Symbolic name meaning all available interfaces
port = 9999  # Arbitrary non-privileged port
num = 0
server_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_sock.bind((host, port))
while True:
    server_sock.listen(1)
    print("기다리는 중")
    client_sock, addr = server_sock.accept()
    print("연결 완료")
    data = client_sock.recv(4096).decode()
    if data == "": break
    print(data)
    count = 0


    while True:
        data = client_sock.recv(4096).decode()
        print("receive data")
        if data == "" : break
        print(data)

    client_sock.close()
    print("연결 종료")

server_sock.close()
