public class Player {
    private int bridgeCard = 0;
    private String toolCardString = "";
    private int finishScore = 0;
    private final int playerId;
    private int currPosition = 0;

    public Player(int playerId){
        this.playerId = playerId;
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
        toolCardString += cellType;

    }
    public void addFinishScore(int ranking){
        switch (ranking){
            case 0 -> finishScore = 7;
            case 1 -> finishScore = 3;
            case 2 -> finishScore = 1;
        }
    }

    public int getScore(){
        int score = 0;
        for(int i = 0; i< toolCardString.length(); i++){
            switch (toolCardString.toCharArray()[i]) {
                case 'P' -> score += 1;
                case 'H' -> score += 2;
                case 'S' -> score += 3;
            }
        }
        score += finishScore;
        return score;
    }

    public String getToolCardString(){
        return toolCardString;
    }

    public int getCurrPos() {
        return currPosition;
    }

    public void setCurrPos(int cellIndex) {
        this.currPosition = cellIndex;
    }

    public int getPlayerId(){
        return playerId;
    }

    public int getBridgeCard() {
        return bridgeCard;
    }
}
