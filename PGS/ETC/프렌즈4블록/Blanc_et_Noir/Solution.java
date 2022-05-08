class Solution {
	
	//���� �� �ִ� ����� �����ϴ��� ã��, �����Ѵٸ� ����� ����� ���� ��� ���� �����ϴ� �޼ҵ�
    public static int find(char[][] map){
    	//�������� ����� ��ġ�� �����ϴ� �ӽ� 2���� �迭
        boolean[][] temp = new boolean[map.length][map[0].length];
        int cnt = 0;
        
        for(int i=0; i<map.length-1; i++){
            for(int j=0; j<map[i].length-1; j++){
            	//������� �ƴ� ����̶��
                if(map[i][j]>='A'&&map[i][j]<='Z'){
                	//���� �� �ִ� �����϶� �� ��ġ ������ �ӽ÷� ������
                    if(map[i][j]==map[i+1][j]&&map[i][j]==map[i][j+1]&&map[i][j]==map[i+1][j+1]){
                        temp[i][j] = true;
                        temp[i+1][j] = true;
                        temp[i][j+1] = true;
                        temp[i+1][j+1] = true;
                    }
                }
            }
        }
        
        //�������� ��ϵ鿡 ���� ���� �迭�� �ش� ������ �ݿ���
        for(int i=0; i<temp.length; i++){
            for(int j=0; j<temp[i].length; j++){
                if(temp[i][j]){
                    map[i][j]='.';
                    //���� Ƚ���� �����
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    //����� ����� �� ���Ŀ� ���� ������ ä��� �޼ҵ�
    public static void arrange(char[][] map){
    	//���� ������ ��, ���� �Ʒ����� ����� Ž����
        for(int j=0; j<map[0].length; j++){
        	//������ ���� ������ ����
            int cnt = 0;
            for(int i=map.length-1; i>=0; i--){
            	//�����̶�� ������ ���� ������Ŵ
                if(map[i][j]=='.'){
                    cnt++;
                //������ �ƴ϶�� �̴� ����̹Ƿ� �ش� ����� ���鸸ŭ �̵���Ŵ
                //��, ������ �����ٸ� ���� �̵���Ű�� ����
                }else if (cnt!=0){
                    map[i+cnt][j] = map[i][j];
                    map[i][j] = '.';
                }
            }
        }
    }
    
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        
        char[][] map = new char[m][n];
        
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length(); j++){
                map[i][j] = board[i].charAt(j);
            }
        }

        int cnt;
        
        //���� �� �ִ� ����� ���� 0�϶����� �ݺ�
        while((cnt = find(map))!=0){
            answer += cnt;
            //����� ����� ���� ��������
            arrange(map);
        }
        
        return answer;
    }
}