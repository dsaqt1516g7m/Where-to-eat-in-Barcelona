

$( "#form-login" ).submit(function( event ) {
console.log("first");
  event.preventDefault();
  login($("#inputLoginid").val(), $("#inputPassword").val(), function(){
  	console.log("change");
  	window.location.replace('main.html');
  });
});

