number_lookup = {
'777777':1,
'888888':2,
'878887':3,
'788878':4,
'878777':5,
'777878':6,
'888878':7,
'878888':8,
'778777':9,
'777877':10,
'888777':11,
'777888':12,
'777787':13,
'787777':14,
'888788':15,
'887888':16,
'877887':17,
'788778':18,
'888877':19,
'778888':20,
'787887':21,
'788787':22,
'788888':23,
'888887':24,
'777887':25,
'788777':26,
'788887':27,
'877778':28,
'878878':29,
'787787':30,
'877788':31,
'887778':32,
'777788':33,
'887777':34,
'787888':35,
'888787':36,
'778787':37,
'787877':38,
'878788':39,
'887878':40,
'788877':41,
'778887':42,
'877777':43,
'777778':44,
'877888':45,
'888778':46,
'877878':47,
'878778':48,
'877787':49,
'787778':50,
'887887':51,
'788788':52,
'778788':53,
'887877':54,
'887787':55,
'787788':56,
'778778':57,
'877877':58,
'778878':59,
'878877':60,
'778877':61,
'887788':62,
'878787':63,
'787878':64}
		gap = "<div id='gap'></div>";
		lines = {6:'- - *' ,7 : '---' ,8 : '- -' ,9 : '--- *' } //documentation
		lines = {
			6: "<div id='yang-moving'><div id='yin-moving'></div></div>",
			7 : "<div id='yang'></div>" ,
			8 : "<div id='yang'><div id='yin'></div></div>",
			9 : "<div id='yang-moving'></div>" 
		};
		f = function(){ return Math.random() > 0.5 ? 3 : 2;}
		f3 = function() { return f() + f() + f(); }
		r = function(prev, current, index ,array){ 
					return prev + lines[current] + gap; 
				}
		function loadHex(el) {
			hex =  [f3(),f3(),f3(),f3(),f3(),f3()];
			//console.log('generated hex:' + hex);
			hex.reverse();
			hex_id = hex.reduce( function(a,b,c,d){ return a +""+ b; } );  
			var d = document.getElementById(el);
			var h = hex.reduce(r,gap);
			//console.log( hex );
			var number =  number_lookup[hex_id.replace(/9/g,'7').replace(/6/g,'8')]; //regex ftw
			//console.log( hex_id + ":: " +number);
			d.innerHTML = h;
			var number_div = document.getElementById('number');
			number_div.innerHTML = '<h1>'+number +'</h1>';
			return hex.reverse().reduce( function(a,b,c,d){ return a +""+ b; } );
		}
