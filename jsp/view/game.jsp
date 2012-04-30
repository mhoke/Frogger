<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--  
<html>
	<head>
		<title>Game</title>
	</head>
	<body>
		It worked
	</body>
</html> -->
<html>
	<head>
		<title>Game</title>
		<style type="text/css">
		#canvas {
			border: 6px solid black;
		}
		</style>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
		<!-- colors - BLACK, BLUE, YELLOW, RED, GREEN, WHITE -->
		<script type="text/javascript">
			var map = '${game.level.currentMap.encodedRepresentation}';
			
			var Grid_Size = 10;
			var color = ['#000000', '#0000FF', '#FFFF00', '#FF0000', '#008000', '#008000', '#FFFFFF'];
			var currentColor = color[0];
			var index, ch, colorIndex;
			var keyPress = 0;
			var Green = 1;
			
			function keyfunction(e)
			{
				if(e.keyCode <= 40 && e.keyCode >= 37)
				{
					keyPress = e.keyCode;
				}
			}
			
			function submitform()
			{
			  	document.Form.submit();
			}
			
			function draw()
			{
				var ctx = $("#canvas").get(0).getContext("2d");
				ctx.clearRect(0, 0, 500, 500);
				for(var i = 0; i < Grid_Size; i ++)
				{
					for(var j = 0; j < Grid_Size; j ++)
					{
						index = i*Grid_Size + j;
						ch = map.charAt(index);
						
						colorIndex = ch.charCodeAt(0) - 'a'.charCodeAt(0);
						 
						currentColor = color[colorIndex];
						ctx.fillStyle = currentColor;						
						if(colorIndex > 0 && colorIndex < 4)
						{
							if(Green == 1 && colorIndex == 1)
							{
								ctx.fillStyle = color[4];
								ctx.fillRect(i * 50, (Grid_Size - j - 1) * 50, 50, 50);
								ctx.fillStyle = currentColor;
							}
							
							ctx.beginPath();
							ctx.arc(i * 50 + 25, (Grid_Size - j - 1) * 50 + 25, 25, 0, Math.PI * 2, true);
							ctx.fill();
						}
						else
						{
							ctx.fillRect(i * 50, (Grid_Size - j - 1) * 50, 50, 50);
						}
					}
				}
			}
			
			$(document).ready(function() 
			{
				setTimeout("tick()", 0);
					
				window.tick = function()
				{
					$.ajax({
					  type: 'POST',
					  url: '/frogger/updateGame',
					  data: { key: ''+keyPress },
					  success:
					    function(data, textStatus, jqXHR) {
					    	
					    	keyPress = 0;	
					    	
					      // update the map
					      map = data.map;
					      Green = data.green;
					      
					      // schedule next frame of animation
					      if(map.charAt(0) != 0)
					      {
					      	// redraw the map
					      	draw();
					      	setTimeout("tick()", 300);
					      }
					      else
					      {
					      	submitform();
					      }
					    },
					  error:
					    function(jqXHR, textStatus, errorThrown) {
					      // FIXME: do something
					      alert("We are hosed!");
					    },
					  dataType: 'json'
					});
				};
			});
		</script>
	</head>
	<body onkeypress="keyfunction(event)">
		<form name="Form" action="${pageContext.servletContext.contextPath}/game" method="post">
			<div>
				<canvas id="canvas" width="500" height="500"></canvas>
			</div>
		</form>
	</body>
</html>