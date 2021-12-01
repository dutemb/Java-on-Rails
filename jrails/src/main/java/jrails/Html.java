package jrails;

public class Html {
    private String c;

    public Html(){
        c = "";
    }

    public Html(String s){
        this.c = s;
    }

    public String toString() {
        return this.c;
    }

    public Html seq(Html h) {
       return new Html(this.c+h.c);
    }

    public Html br() {
        return new Html(this.c + "<br/>");
    }

    public Html t(Object o) {
        return new Html(this.c + o.toString());
    }

    public Html p(Html child) {
         return new Html(this.c + "<p>"+child.c+"</p>");
    }

    public Html div(Html child) {
         return new Html(this.c + "<div>"+child.c+"</div>");
    }

    public Html strong(Html child) {
         return new Html(this.c + "<strong>"+child.c+"</strong>");
    }

    public Html h1(Html child) {
         return new Html(this.c + "<h1>"+child.c+"</h1>");
    }

    public Html tr(Html child) {
         return new Html(this.c + "<tr>"+child.c+"</tr>");
    }

    public Html th(Html child) {
         return new Html(this.c + "<th>"+child.c+"</th>");
    }

    public Html td(Html child) {
         return new Html(this.c + "<td>"+child.c+"</td>");
    }

    public Html table(Html child) {
         return new Html(this.c + "<table>"+child.c+"</table>");
    }

    public Html thead(Html child) {
         return new Html(this.c + "<thead>"+child.c+"</thead>");
    }

    public Html tbody(Html child) {
        return new Html(this.c + "<tbody>"+child.c+"</tbody>");
    }

    public Html textarea(String name, Html child) {
         return new Html(c + "<textarea name=\"" + name + "\">" + child.c + "</textarea>");
    }

    public Html link_to(String text, String url) {
         return new Html(c + "<a href=\"" + url + "\">" + text + "</a>");
    }

    public Html form(String action, Html child) {
        return new Html(c + "<form action=\"" + action + "\" accept-charset=\"UTF-8\" method=\"post\">" + child.c + "</form>");
    }

    public Html submit(String value) {
        return new Html(c + "<input type=\"submit\" value=\"" + value + "\"/>");
    }
}