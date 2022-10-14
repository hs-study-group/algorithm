n, r, c = map(int, input().split())

def dac(n, r, c):
    if n == 1:
        if r==0 and c==0:
            return 0
        elif r==0 and c==1:
            return 1
        elif r==1 and c==0:
            return 2
        else:
            return 3
    else:
        r_q = r//(2**(n-1))
        c_q = c//(2**(n-1))
        temp = dac(n-1, r%(2**(n-1)), c%(2**(n-1)))
        return (2*(r_q)+(c_q))*((2**(n-1))**2) + temp
        
print(dac(n, r, c))