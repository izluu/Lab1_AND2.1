package fpoly.account.ph56774_thi19.Model;

public class SanPham {
    private String title,content,date,style,src;
    private int id;

    public SanPham(String title, String content, String date, String style, String src, int id) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.style = style;
        this.src = src;
        this.id = id;
    }

    public SanPham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
