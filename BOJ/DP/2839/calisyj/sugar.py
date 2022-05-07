'''
Problem: 설탕 배달
reference: https://puleugo.tistory.com/27?category=935212
'''

# input
N = int(input())

def answer():
    global N
    if N % 5 == 0:
        five_count = N//5
        return five_count
    else:
        bag = 0
        while N>0:
            N = N-3
            bag = bag+1
            if N % 5 == 0:
                bag = bag + (N//5)
                return bag
            elif N==1 or N==2:
                return -1
            else:
                if N==0:
                    return bag


# output
print(answer())
