'''
Baekjopn : 대회가 끝나고 난 뒤 빰빠빰
'''
n  = int(input()) 
location_radius_list = [tuple(map(int, input().split())) for _ in range(n)]  
stk = []

for xj, yj in location_radius_list: 
  while stk:                           
    xi, yi = stk[-1]
    yj = min(yj, (xj-xi)**2 / (4*yi))
    if yj >= yi: stk.pop()
    else: break
  stk.append((xj, yj))
  print("%.3f" % yj)
  

