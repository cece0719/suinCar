<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
<div data-role="page">

    <div data-role="header">
        <h1>Suin's home</h1>
    </div><!-- /header -->

    <div class="ui-content" role="main">
        <#list list as one>
            <#if one["type"] == "onOff">
                <ul data-role="listview" data-inset="true" style="margin:0 0 1em">
                    <li data-role="list-divider">${one["name"]}</li>
                    <li class="ui-field-contain">
                        <div data-role="navbar">
                            <ul>
                                <li><a href="javascript:;" data-icon="check" onclick="call('${one["url"]}','on');return false;" <#if one["isOn"]>class="ui-btn-active"</#if>>On</a></li>
                                <li><a href="javascript:;" data-icon="delete" onclick="call('${one["url"]}','off');return false;" <#if !(one["isOn"])>class="ui-btn-active"</#if>>Off</a></li>
                            </ul>
                        </div><!-- /navbar -->
                    </li>
                </ul>
            </#if>
        </#list>
    </div><!-- /footer -->
</div>
<script>
    function call(url, status) {
        $.ajax({
            url : url,
            type : 'post',
            dataType: "json",
            async: false,
            data : {
                'status' : status
            },
            success : function(data) {
            }
        })
    }
</script>
</body>
</html>