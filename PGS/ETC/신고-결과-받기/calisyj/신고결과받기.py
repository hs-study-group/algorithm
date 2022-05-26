from collections import defaultdict

def solution(id_list, report, k):
    answer = []
    dict = defaultdict(list)
    dict_count = {}
    for id in id_list: # id별 신고 당한 횟수 count dict 생성
        dict_count[id] = 0
        
    for text in report: # id별 report dict 생성
        lst = text.split()
        if lst[1] in dict[lst[0]]:
            pass
        else:
            dict[lst[0]].append(lst[1])
    
    for key in dict: # 신고 당한 횟수 최신화
        for value in dict[key]:
            dict_count[value] = dict_count[value]+1
    
    ban_list = []
    for key in dict_count: # ban list 생성
        if dict_count[key] >= k:
            ban_list.append(key)
    
    a = {}
    for key in id_list:
        a[key] = 0  

    for key in dict:
        for ban in ban_list:
            if ban in dict[key]:
                a[key] =  a[key]+1

    for key in a:
        answer.append(a[key])
        
    return answer
