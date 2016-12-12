package youlook.entities;

public class Record {
  private String m_string;
  private int m_count;

  public Record(String str, int count) {
    m_string = str;
    m_count = count;
  }

  public String getString() {
    return m_string;
  }

  public int getCount() {
    return m_count;
  }
}
