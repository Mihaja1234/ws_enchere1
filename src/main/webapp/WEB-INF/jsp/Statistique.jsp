<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%
List<Object[]> liste = (List<Object[]>) request.getAttribute("graphe");
List<Object[]> NombreTotalProduitVendu = (List<Object[]>) request.getAttribute("NombreTotalProduitVendu");
List<Object[]> StatMembre = (List<Object[]>) request.getAttribute("StatMembre");
List<Object[]> StatEnchere = (List<Object[]>) request.getAttribute("StatEnchere");

int annee = (int) request.getAttribute("annee");
int annee_actuelle=LocalDate.now().getYear();
%>
<!DOCTYPE html>
<html>
<head>
<title>Graphique de l'évolution du chiffre d'affaires</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic">
    <link rel="stylesheet" href="assets/css/Contact-Form-Clean.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg fixed-top" id="mainNav">
        <div class="container">
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="login_admin.html">Login Admin</a></li>

                    <li class="nav-item"><a class="nav-link" href="/backOffice">Accueil admin</a></li>

                </ul>
            </div>
        </div>
    </nav>
<h1>Evolution chiffre d`affaire <%= annee %></h1>
<canvas id="myChart"></canvas>

<script>
  <% if (liste != null && !liste.isEmpty()) {
   for(int i=0;i<liste.size();i++) { %>
var salesData = [
  { month: <%= liste.get(i)[0]  %>, sales: <%= liste.get(i)[2]  %> }
];
 <% } %>
 <% } else { %>
var salesData = [
  { month: "Janvier", sales: 0 },
  { month: "Février", sales: 0 },
  { month: "Mars", sales: 0 },
  { month: "Avril", sales: 0 },
  { month: "Mai", sales: 0 },
  { month: "Juin", sales: 0 }
];
 <% } %>

var months = salesData.map(function(data) {
  return data.month;
});

var sales = salesData.map(function(data) {
  return data.sales;
});

var ctx = document.getElementById("myChart").getContext("2d");
var myChart = new Chart(ctx, {
  type: "line",
  data: {
    labels: months,
    datasets: [
      {
        label: "Chiffre d'affaires",
        data: sales,
        backgroundColor: "rgba(255, 99, 132, 0.2)",
        borderColor: "rgba(255, 99, 132, 1)",
        borderWidth: 1
      }
    ]
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});
</script>
 <header class="masthead" style="background-image:url('assets/img/');">
        <div class="overlay"><span class="meta">Posted by&nbsp;<a href="#">Start Bootstrap</a>&nbsp;on August 24, 2018</span><a href="#">Start Bootstrap</a></div>
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-lg-8 mx-auto">
                    <div class="post-heading">
                    <span class="meta">Choisir Année:
                    <h3><a href="/graphe/<%= annee-1 %>">  <input type="submit" value="-"><%= annee %>
                    <a href="/graphe/<%= annee+1 %>"> <input type="submit" value="+" ></a>
                    </span>

               <p><span class="meta">Nombre total des produits vendus par catégorie:</span></p>
                        <div class="table-responsive">
                            <table class="table" border="3">
                                <thead>
                                    <tr>
                                        <th>Categorie Produit</th>
                                        <th>Nombre produit vendus</th>
                                    </tr>
                                </thead>
                             <tbody>
                                 <% for(int i=0;i<NombreTotalProduitVendu.size();i++) { %>
                            <tr>
                                <td><%= NombreTotalProduitVendu.get(i)[1]  %></td>
                                <td><%= NombreTotalProduitVendu.get(i)[2]  %></td>
                            </tr>
                            <% } %>
                          </tbody>
                          </table>

                    <p><span class="meta">Statistique sur les membres:</span></p>
                        <div class="table-responsive">
                         <table class="table" border="3">
                         <thead>
                             <tr>
                                 <th>Année</th>
                                  <th>Mois</th>
                                   <th>Nombre total inscrit</th>
                                   </tr>
                                   </thead>
                        <tbody>
                             <% for(int i=0;i<StatMembre.size();i++) { %>
                        <tr>
                            <td><%= StatMembre.get(i)[1]  %></td>
                            <td><%= StatMembre.get(i)[2]  %></td>
                            <td><%= StatMembre.get(i)[0]  %></td>
                        </tr>
                        <% } %>
                      </tbody>
                </table>
         <p><span class="meta">Statistique sur les enchères:</span></p>
             <div class="table-responsive">
              <table class="table" border="3">
                 <thead>
                     <tr>
                         <th>Année</th>
                         <th>Mois</th>
                         <th>Nombre total enchères effectués</th>
                         </tr>
                         </thead>
                <tbody>
             <% for(int i=0;i<StatEnchere.size();i++) { %>
                <tr>
                    <td><%= StatEnchere.get(i)[1]  %></td>
                    <td><%= StatEnchere.get(i)[2]  %></td>
                    <td><%= StatEnchere.get(i)[0]  %></td>
                </tr>
                <% } %>
              </tbody>
            </table>
            </div>
           </div>
         </div>
       </div>
      </div>
    </header>
    <article></article>
        <footer></footer>
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/clean-blog.js"></script>

</body>
</html>
