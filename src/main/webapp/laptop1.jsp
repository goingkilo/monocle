<html>
<style>
@font-face {
	font-family: 'oldnews';
	src: url('OldNewspaperTypes.ttf') format('truetype');
}

@font-face {
	font-family: 'moms';
	src: url('Moms_typewriter-webfont.ttf') format('truetype');
}

#searchbutton {
	width: 60px;
	height: 20px;
	background-color: grey;
	display: block;
}

#box {
	width: 600px;
	border: 5px;
	margin-left: 100px;
	padding:10px;
	overflow: auto;
}

#sku {
	font-family: oldnews;
}

#desc {
	font-family: moms;
	border:3px;
}

img {
	float: right;
}
</style>
<body>
	<h3>How to buy a laptop !</h3>

	<script src="./jquery.js">
	</script>

	<script>

		var template = "<div id='box'>\
                        <img src=\"@image\">\
                        <div id='sku' hidden>@sku</div>\
                        <div hidden>@nname</div>\
                        <div id='desc' >@shortDescription</div>\
                </div>";

	
		$('document').ready(
			function() {
			$('#searchbutton').click(
				function(e) {
					$.ajax({
						type : "POST",
						url : '/mwmonocle/rest/product/query/',
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
						console.log(key + ":" + product[key]);
						a = a.replace( '@'+key, product[key]);
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
			<input type="text" name='expr' value='laptop*'> 
			<input type='text' name='show' value="image,sku,name,shortDescription,regularPrice"> 
			<input type='text' name='page' value='1' hidden> 
			<input type='text' name='pagesize' value='5' >
		</form>
		<button id="searchbutton">Get</button>
	</div>
	<div id="result"></div>
	<div id="raw" hidden></div>
</body>
</html>
