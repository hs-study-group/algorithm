def solution(array, commands):
    answer = []
    for list in commands:
        lst = array[(list[0]-1):(list[1])]
        lst.sort()
        answer.append(lst[list[2]-1])

        
    return answer
