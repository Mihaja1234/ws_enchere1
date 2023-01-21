<%@page import="java.util.List"%>
<%@page import="Enchere.models.Admin"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%
String token = (String)request.getSession().getAttribute("token");
Admin admin = (Admin) request.getSession().getAttribute("admin");
float chiffreAffaire = (float) request.getAttribute("chiffreAffaire");
%>
<!DOCTYPE html>
<html lang="en">
<head>
     <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Back Office</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/Contact-Form-Clean.css">
</head>
<body>
 <h1>Back Office AdministrTateur</h1>
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

     <header class="masthead" style="background-image:url('assets/img/');">
         <div class="overlay"></div>
         <div class="container">
             <div class="row">
                 <div class="col-md-10 col-lg-8 mx-auto">
                     <div class="site-heading">
                         <h4>Chiffre d`affaire :  <%= chiffreAffaire  %></h4>
                         <br> <br>
                         <form action="/newCategorie" method="POST">
                           <input type="text" name="typeCategorie" placeholder="type categorie">
                           <input type="submit" value="Enregistrer">
                           </form>

                        <div class="">
                              <a href="/ListeRechargementCompte">Voir la liste des demandes de rechargement de compte</a>
                        </div>
                        <br>
                        <div class="">
                         <a href="/Statistique">Voir Statistique</a>
                    </div>
                     <div class="">
                         <a href="/logout">Se déconnecter</a>
                         </div>
                     </div>
                 </div>
             </div>
         </div>
     </header>
     <hr>
     <footer></footer>
     <script src="/assets/js/jquery.min.js"></script>
     <script src=""></script>
     <script src="/assets/js/clean-blog.js"></script>



</body>
</html>