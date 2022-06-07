public class Player {
    private int bridgeCard;
    private int score;
    private final int playerId;
    private int currPosition;

    public Player(int playerId){
        this.bridgeCard = 0;
        this.score = 0;
        this.playerId = playerId;
        this.currPosition = 0;
    }

    public int getMvmtAbs(int diceResult){
        int mvmt = diceResult - bridgeCard;
        return Math.max(mvmt, 0);
    }
    public void addBridgeCard(){
        bridgeCard++;
    }
    public void deductBridgeCard(){
        if(bridgeCard > 0) {
            bridgeCard--;
        }
    }
    public void addToolScore(String cellType) {
        switch (cellType) {
            case "P" -> score += 1;
            case "H" -> score += 2;
            case "S" -> score += 3;
        }
    }
    public void addFinishScore(int ranking){
        switch (ranking){
            case 0 -> score += 7;
            case 1 -> score += 3;
            case 2 -> score += 1;
        }
    }

    public int getScore(){
        return score;
    }

    public int getCurrPos() {
        return currPosition;
    }

    public void setCurrPos(int cellIndex) {
        this.currPosition = cellIndex;
    }
}
