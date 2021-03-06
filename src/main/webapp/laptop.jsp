<html>
<style>

#searchbutton {
	width: 60px;
	height: 20px;
	background-color: grey;
	display: block;
}

#box {
	width: 600px;
	border: 1px;
	border-style:solid;
	margin-left: 100px;
	padding:10px;
	overflow: auto;
}

#desc {
	border:3px;
}

img {
	float: right;
}
#name1{
	border-style:solid;
	border: 1px;
}
</style>
<body>
	<h3>How to buy a laptop !</h3>

	<script src="./jquery.js">
	</script>

	<script>

		var template = "<div id='box'>\
                        <div id='name1'>@name</div>\
                        <a href=\"@addToCartUrl\" ><img src=\"@image\"></a>\
                        <div id='sku' hidden>@sku</div>\
                        <div id='desc' >@shortDescription</div>\
                        <div id='price' >$@regularPrice</div>\
                </div>";

	
		$('document').ready(
			function() {
			$('#searchbutton').click(
				function(e) {
					$.ajax({
						type : "POST",
						url : '/rest/product/query/',
						data : {
							'expr' : $('input[name=expr]').val(),
							'show' : $('input[name=show]').val(),
							'page' : $('input[name=page]').val(),
							'pagesize' : $('input[name=pagesize]')
							.val(),
							},
							dataType : "json",
							error : function(x) {
							var a = JSON.stringify(x)
							$('#result').html(json2html(a));
							
						},
						success : function(x) {
							var a = JSON.stringify(x)
							$('#result').html(json2html(a));
						}
					});
				})

				function json2ProductDiv(keys,product){
					a = template;
					for( var x = 0 ; x < keys.length ; x++ ) {
						key = keys[x];
						value = product[key]
						if( key == 'shortDescription') {
						value = value.replace( /\n/g, '<br>')
						}
						console.log(key + ":" +value); 
						a = a.replace( '@'+key, value);
					};
					return a;
				}
				function json2html(x) {
					var ret = '<div>'
					json = JSON.parse(x)
					products = json.products
					if( products == null ) {
						return "<h3>no rows found</h3>";
					}
					keys = $('input[name=show]').val().split(',');
					
					var result = ''
					var tmp = template;
					for (i in products) {
						productHTML = json2ProductDiv( keys, products[i])
						result = result +  productHTML  ;
					}
				
						
					return result;
				}
			})
	</script>
	<div>
		<form name='someform'>
			<input type="text" name='expr' value='Acer'> 
			<input hidden type='text' name='show' value="image,sku,name,shortDescription,longDescription,regularPrice,addToCartUrl"> 
			<input hidden type='text' name='page' value='1' > 
			<input hidden type='text' name='pagesize' value='5' >
		</form>
		<button id="searchbutton">Get</button>
	</div>
	<div id="result"></div>
	<div id="raw" hidden></div>
</body>
</html>
