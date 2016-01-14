

$("#create_boton").click(function(e) {
	e.preventDefault();
	if( $("#name").val() == "" || $("#description").val() == "" || $("#avgprice").val() == "" || $("#address").val() == "" || $("#phone").val() == "" || $("#lat").val() == "" || $("#lng").val() == "")
	{
		if($("#name").val() == "")
			{
				document.getElementById('name').style.background='#F6B5B5';
				$('#name').attr('placeholder','Fill it!');
			}
		if($("#description").val() == "")
			{
				document.getElementById('description').style.background='#F6B5B5';
				$('#description').attr('placeholder','Fill it!');
			}
		if($("#avgprice").val() == "")
			{
				document.getElementById('avgprice').style.background='#F6B5B5';
				$('#avgprice').attr('placeholder','Fill it!');
			}
		if($("#address").val() == "")
			{
				document.getElementById('address').style.background='#F6B5B5';
				$('#address').attr('placeholder','Fill it!');
			}
		if($("#phone").val() == "")
			{
				document.getElementById('phone').style.background='#F6B5B5';
				$('#phone').attr('placeholder','Fill it!');
			}
		if($("#lat").val() == "")
			{
				document.getElementById('lat').style.background='#F6B5B5';
				$('#lat').attr('placeholder','Fill it!');
			}
		if($("#lng").val() == "")
			{
				document.getElementById('lng').style.background='#F6B5B5';
				$('#lng').attr('placeholder','Fill it!');
			}
	}
	else{
			var create = new Object();
			create.name=  $("#name").val();
			create.description = $("#description").val();
			create.avgprice = $("#avgprice").val();
			create.address = $("#address").val();
			create.phone = $("#phone").val();
			create.lat = $("#lat").val();
			create.lng = $("#lng").val();
			CreateRes(create);		
	    }
});