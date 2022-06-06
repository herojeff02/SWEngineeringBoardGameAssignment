public class Player {
    private int bridgeCard;
    private int score;
    private final int playerId;
    int currPosition;

    public Player(int playerId){
        this.bridgeCard = 0;
        this.score = 0;
        this.playerId = playerId;
        this.currPosition = 0;
    }

    public int getMvmtAbs(){
        int mvmt = Ruler.diceRoll() - bridgeCard;
        return Math.max(mvmt, 0);
    }
    public int[] getMvmtRange(boolean isFreeMovable){
        int[] range;
        if(isFreeMovable){
            range = new int[] {-getMvmtAbs(), getMvmtAbs()};
        }
        else{
            range = new int[] {getMvmtAbs(), getMvmtAbs()};
        }
        return range;
    }
    public void addBridgeCard(){
        bridgeCard++;
    }
    public void removeBridgeCard(){
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
    public void addScore(int score){
        this.score += score;
    }


}
