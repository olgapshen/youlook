package youlook.logic;

public class DigitCounter
{
  public int countDigits(String str) {
    int count = 0;

    for (int i = 0, len = str.length(); i < len; i++) {
        if (Character.isDigit(str.charAt(i))) {
            count++;
        }
    }

    return count;
  }
}
