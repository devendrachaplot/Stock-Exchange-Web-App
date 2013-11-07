<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Vector"%>
<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
            
            
            <%
            Vector price = (Vector)session.getAttribute("data_price");
            Vector date = (Vector)session.getAttribute("data_date");
            int size = price.size();
            %>
            
            
         
            var data = new google.visualization.DataTable();
            data.addColumn('number','Price');
            data.addColumn('number', 'Date');
            
           <% int x=0;
            System.out.println(size);
            out.print("data.addRows("+size+");");
            while(x<size){
                System.out.print(x+" "+size+" "+price.get(x)+" "+date.get(x)+"\n");
               
                out.print("data.setValue("+x+",0,"+price.get(x)+");");
                out.print("data.setValue("+x+",1,"+price.get(x)+");");
                x++;
            }
            
            %>
        var options = {
          title: 'Company Performance'
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
  </body>
</html>