<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
  	<title>Payroll</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">
		
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/CSS/style.css">
    <link rel="stylesheet" href="resources/CSS/stylesheet.css">
  </head>
  <body>		
		<div class=" sidebarContainer wrapper d-flex align-items-stretch">
			<nav id="sidebar">
				<div class="p-4 pt-5">
          <a href="#" class="img logo rounded-circle mb-5" style="background-image: url(logo.jpg);"></a>
          <security:authorize access="hasRole('CONSULTANT')">

	        <ul class="list-unstyled components mb-5">
	          <li class="active">
	            <a href="#EmpStructSubMenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Employee Structure</a>
	            <ul class="collapse list-unstyled" id="EmpStructSubMenu">
                <li>
                    <a href="views/EmpStructureViews/createEmpStruct.html">Create Employee Structure</a>
                </li>
                <li>
                    <a href="views/EmpStructureViews/editEmpStruct.html">Edit Employee Structure</a>
                </li>
                <li>
                    <a href="views/EmpStructureViews/assignPayTypeToEmpStruct.html">Assign Paytype to Employee Structure</a>
                </li>
	            </ul>
	          </li>
	          <li>
              <a href="#compStructSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Company Structure</a>
              <ul class="collapse list-unstyled" id="compStructSubmenu">
                <li>
                  <a href="views/CompanyStructViews/createCompanyStruct.html">Create Company Structure</a>
              </li>
              <li>
                <a href="views/CompanyStructViews/editCompanyStruct.html">Edit Company Structure</a>
              </li>
              <li>
                <a href="views/CompanyStructViews/assignPayTypeToCompanyStruct.html">Assign Paytype to Company Structure</a>
              </li>
              </ul>
	          </li>
             <li>
               <a href="#payTypeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Pay Types</a>
               <ul class="collapse list-unstyled" id="payTypeSubmenu">
                 <li>
                     <a href="views/PayTypes/createPayType.html">Create Pay Types</a>
                 </li>
                 <li>
                     <a href="views/PayTypes/editPayType.html">Edit Pay Types</a>
                 </li>
            
               </ul>
              </li>



               <li>
                <a href="#payrollStructureSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Payroll Structure</a>
                <ul class="collapse list-unstyled" id="payrollStructureSubmenu">
                  <li>
                      <a href="views/PayrollStructure/createPayrollStructure.html">Create Payroll Structure</a>
                  </li>
                  <li>
                      <a href="views/PayrollStructure/editPayrollStructure.html">Edit Payroll Structure</a>
                  </li>
             
                </ul>
               </li>
 
               <li>
                <a href="#gradingStructureSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Grading and Salary Structure</a>
                <ul class="collapse list-unstyled" id="gradingStructureSubmenu">
                  <li>
                      <a href="views/GradingAndSalaryViews/createGradingStructure.html">Create Grading and Salary Structure</a>
                  </li>
                  <li>
                      <a href="views/GradingAndSalaryViews/editGradingStructure.html">Edit Grading and Salary Structure</a>
                  </li>
             
                </ul>
               </li>


               <li>
                <a href="#financialStructureSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Financial Structure</a>
                <ul class="collapse list-unstyled" id="financialStructureSubmenu">
                  <li>
                      <a href="views/FinancialStructureViews/banks.html">Banks</a>
                  </li>
                  <li>
                      <a href="views/FinancialStructureViews/costCenters.html">Cost Centers</a>
                  </li>

                  <li>
                    <a href="views/FinancialStructureViews/glAccounts.html">GL Accounts</a>
                </li>

                <li>
                  <a href="views/FinancialStructureViews/paymentMethods.html">Payment Methods</a>
              </li>
             
                </ul>
               </li>

	        </ul>
        </security:authorize>
	        <div class="footer">
	        	<p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | Cloud Consulting Services <i class="icon-heart" aria-hidden="true"></i> <a href="https://www.linkedin.com/company/cloud-consulting-services-mena/" target="_blank">Linkedin</a>
						  <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
	        </div>

	      </div>
    	</nav>

        <!-- Page Content  -->
      <div id="content" class=" p-4 p-md-5">

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container-fluid">

            <button type="button" id="sidebarCollapse" class="btn btn-primary">
              <i class="fa fa-bars"></i>
              <span class="sr-only">Toggle Menu</span>
            </button>
            <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fa fa-bars"></i>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="nav navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="http://localhost:8080/Payroll">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Portfolio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
      

        <img src="resources/images/background.jpg"  id="imageBackground" >


      </div>


    </div>
    
    <script src="resources/JS/jquery.min.js"></script>
    <script src="resources/JS/popper.js"></script>
    <script src="resources/JS/bootstrap.min.js"></script>
    <script src="resources/JS/main.js"></script>
  </body>
</html>