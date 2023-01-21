<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Login administrateur</title>
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
    <section class="contact-clean">
     <form method="POST" action="/login" class="login-form">

         <h2 class="text-center">Se connecter</h2>
          <div class="form-group"><input class="form-control is-invalid" type="email" name="email" placeholder="Email" value="${email}"><small class="form-text text-danger">Please enter a correct email address.</small></div>
          <div class="form-group"><input class="form-control" type="password" name="mdp" placeholder="Mot de passe" value="${mdp}"></div>
          <div class="form-group"><button class="btn btn-primary" type="submit">Connexion </button></div>
           <div class="form-group">
            <div class="form-group d-md-flex">
            <%
               if(request.getParameter("error")!=null){ %>
               <p style="color:red;">Connexion échouée, veuillez réessayer</p>
                <% } %>
               </div>

     </form>
      </section>
     <script src="assets/js/jquery.min.js"></script>
      <script src="assets/bootstrap/js/bootstrap.min.js"></script>
       <script src="assets/js/clean-blog.js"></script>

</body>
</html>
