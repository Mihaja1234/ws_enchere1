<%@page import="java.util.List"%>
<%@page import="Enchere.models.Admin"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%
String token = (String)request.getSession().getAttribute("token");
Admin admin = (Admin) request.getSession().getAttribute("admin");
float chiffreAffaire = (float) request.getAttribute("chiffreAffaire");
List<Object[]> liste = (List<Object[]>) request.getAttribute("listeRechargementCompte");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>backOffice</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic">
    <link rel="stylesheet" href="assets/css/Contact-Form-Clean.css">
</head>
<body>
    <nav class="navbar navbar-light navbar-expand-lg fixed-top" id="mainNav">
        <div class="container">
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="/login">Login Admin</a></li>

                    <li class="nav-item"><a class="nav-link" href="/backOffice">Accueil admin</a></li>
                </ul>
            </div>
        </div>
    </nav>

 <h1>Back Office Administrateur</h1>
     <header class="masthead" style="background-image:url('assets/img/contact-bg.jpg');">
         <div class="overlay"></div>
         <div class="container">
             <div class="row">
                 <div class="col-md-10 col-lg-8 mx-auto">
                     <div class="site-heading">
                         <h4>Chiffre d`affaire :  <%= chiffreAffaire  %></h4>
                         <br> <br>
                         <h4>Liste des rechargement des comptes</h4>
                         <br> <br>

                         <div class="table-responsive">
                             <table class="table">
                                 <thead>
                                     <tr>
                                         <th>Nom</th>
                                         <th>Prenom</th>
                                         <th>Montant</th>
                                         <th>DateHeure de rechargement</th>
                                         <th></th>
                                     </tr>
                                 </thead>
                                 <tbody>
                                        <% for(int i=0;i<liste.size();i++) { %>
                                             <tr>
                                                 <td><%= liste.get(i)[2]  %></td>
                                                 <td><%= liste.get(i)[3]  %></td>
                                                 <td><%= liste.get(i)[4]  %></td>
                                                 <td><%= liste.get(i)[5] %></td>
                                                 <td><a href="/Validation/<%= liste.get(i)[0] %>/<%= liste.get(i)[1] %>/<%= liste.get(i)[4] %>">Valider</a></td>
                                             </tr>
                                             <% } %>
                                     </tr>

                                 </tbody>
                             </table>
                         </div>
                     </div>
                 </div>
             </div>
         </div>
     </header>
     <hr>
     <footer></footer>
     <script src="assets/js/jquery.min.js"></script>
     <script src="assets/bootstrap/js/bootstrap.min.js"></script>
     <script src="assets/js/clean-blog.js"></script>


 <a href="/Statistique">
