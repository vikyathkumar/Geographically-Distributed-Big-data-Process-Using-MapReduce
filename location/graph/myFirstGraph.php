<html>
<head>
    <title>My first chart using FusionCharts Suite XT</title>
    <script type="text/javascript" src="js/fusioncharts.js"></script>
    <script type="text/javascript" src="js/themes/fusioncharts.theme.fint.js"></script>
<?php
$myfile="data.txt";
$data=file($myfile);

$file_handle = fopen("data.txt", "rb");
$dataInJson = array();
$totalElement =0;
foreach($data as $line)
{
    $str=preg_replace("/(^[\r\n]*|[\r\n\t]+)[\s\t]*[\r\n\t]+/", "", $line); //to remove \n,\t,\r
    $var = explode('	',$str);
//		$arr[$var[0]] = $var[1];
    $element= array('label'=>$var[0],'value'=>$var[1]);
    $dataInJson[$totalElement++]=$element;
}

$output = json_encode($dataInJson);
//var_dump($dataInJson);

?>
    <script type="text/javascript">
        FusionCharts.ready(function(){
            var revenueChart = new FusionCharts({
                "type": "line",
                "renderAt": "chartContainer",
                "width": "1000",
                "height": "500",
                "dataFormat": "json",
                "dataSource":  {
                    "chart": {
                        "caption": "Total Error in log file",
                        "subCaption": "Last week",
                        "xAxisName": "Server",
                        "yAxisName": "No. Of errors",
                        "lineThickness": "2",
                        "paletteColors": "#0075c2",
                        "baseFontColor": "#333333",
                        "baseFont": "Helvetica Neue,Arial",
                        "captionFontSize": "14",
                        "subcaptionFontSize": "14",
                        "subcaptionFontBold": "0",
                        "showBorder": "0",
                        "bgColor": "#ffffff",
                        "showShadow": "0",
                        "canvasBgColor": "#ffffff",
                        "canvasBorderAlpha": "0",
                        "divlineAlpha": "100",
                        "divlineColor": "#999999",
                        "divlineThickness": "1",
                        "divLineDashed": "1",
                        "divLineDashLen": "1",
                        "divLineGapLen": "1",
                        "showXAxisLine": "1",
                        "xAxisLineThickness": "1",
                        "xAxisLineColor": "#999999",
                        "showAlternateHGridColor": "0"
                    },
                    "data": <?php echo $output;?>
                }

            });
            revenueChart.render();
        })
    </script>
</head>
<body>
<div id="chartContainer">FusionCharts XT will load here!</div>
</body>
</html>
