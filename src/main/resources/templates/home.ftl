<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no">
</head>
<body>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<br>
    <div class="ui-field-contain">
        <label for="flip-led">LED</label>
        <select name="flip-led" id="flip-led" data-role="slider" onchange="call(this);">
            <option value="off">Off</option>
            <option value="on">On</option>
        </select>
    </div>
<script>
    function call(e) {
        $.ajax({
            url : '/led',
            type : 'post',
            dataType: "json",
            data : {
                'status' : e.value
            },
            success : function(data) {
            }
        })
    }
</script>
</body>
</html>