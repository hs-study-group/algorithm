'''
name: 행렬의 덧셈
작성자: calisyj
description: 프로그래머스 스킬 체크 레벨1
행렬의 덧셈은 행과 열의 크기가 같은 두 행렬의 같은 행, 같은 열의 값을 서로 더한 결과가 됩니다. 2개의 행렬 arr1과 arr2를 입력받아, 행렬 덧셈의 결과를 반환하는 함수, solution을 완성해주세요.

'''
def solution(arr1,arr2):
    answer = arr1
    for i in range(len(arr1)):
        for j in range(len(arr1[i])):
            answer[i][j] = arr1[i][j]+arr2[i][j]
    return answer

'''후기: 생각보다 쉬웠다. input값을 따로 받아서 배열을 만들어줘야 하는 줄 알았는데
그럴 필요 없이 행렬의 덧셈을 수행하는 함수만 만들어주면 해결되었다.
'''
