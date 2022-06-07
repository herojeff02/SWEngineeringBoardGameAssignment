public class Main {
    public static void main(String[] args) {
        RuleBook ruleBook = new RuleBook(4, "default.map");
        int diceResult = RuleBook.diceRoll();
        boolean result = ruleBook.move(0, "rrdd", diceResult);
        System.out.println(ruleBook.players.get(0).getScore());
    }
}