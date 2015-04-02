<html>
<style>
#searchbutton {
	width: 60px;
	height: 20px;
	background-color: grey;
	display: block;
}

	
#box{
	width:400pxa;
	border:2px;
        border-style: solid;
	border-color:red;
        margin-left:100px;
    	overflow:auto;
}
#sku{
    font-family: "Courier New", Times, Sans-serif;
    font-family: "Arial", Times, serif;
}
#desc{
    font-family: "Arial", Times, serif;
    font-style:italic;
}
img {
	float:right;
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
                        <div id='desc' hidden>@desc</div>\
                </div>";

	
		$('document').ready(
			function() {
			$('#searchbutton').click(
				function(e) {
					$.ajax({
						type : "POST",
						url : '/rest/product/query/',
						data : {
							'expr' : 'longDescription='+ $('input[name=expr]').val(),
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
					keys.map(function(key){
						a = a.replace( '@'+key, product[key])
					});
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
					
					var table = '<table border=2>row</table>'
					var row = '<tr><td>key</td><td>value</td></tr>'
					var tmp = template;
					for (i in products) {
						productHTML = json2ProductDiv( keys, i)
						table = table.replace('row', productHTML + 'row')
						console.log(table);
						table = table.replace('row', '<tr><td>---</td></tr>row')
					}
				
						
					table = table.replace('row', '')
					return table;
				}
			})
	</script>
	<div>
		<form name='someform'>
			<input type="text" name='expr' value='laptop*'> 
			<input type='text' name='show' value="image,sku,name,shortDescription,regularPrice">
			<input type='text' name='page' value='1' hidden> 
			<input type='text' name='pagesize' value='10'hidden>
				
		</form>
		<button id="searchbutton">Get</button>
	</div>
	<div id="result"></div>
	<div id="raw" hidden></div>
</body>
</html>
