<#macro base status='info'>
<html>
<head>
  <title>Alboran Platform</title>
</head>
<body style="font-family: Arial, Helvetica, sans-serif; margin: 0px 0px 0px 0px; padding: 15px 15px 15px 15px; background-color: #fafafa;">
<#switch status>
  <#case 'success'>
    <#assign color = '#5cb85c'>
    <#assign title = 'OK'>
    <#break>
  <#case 'warning'>
    <#assign color = '#f0ad4e'>
    <#assign title = 'Warning'>
    <#break>
  <#case 'error'>
    <#assign color = '#d9534f'>
    <#assign title = 'Error'>
    <#break>
  <#default>
    <#assign color = '#337ab7'>
    <#assign title = 'Notification'>
</#switch>

<div style="width: 100%; background-color: ${color}; padding: 2px 2px 2px 2px; margin: 0px 0px 0px 0px;">

<div style="font-size: 16pt; font-weight: bold; color: white; padding: 10px 10px 10px 10px; ">${title}</div>

<div style="color: black; font-size: 11pt; background-color: #fafafa; padding: 10px 10px 10px 10px;">
    <#nested/>
</div>

<div style="font-size: 10pt; font-style: italic; color: white; padding: 10px 10px 10px 10px; ">
    Email automatically generated on ${timestamp?datetime}
</div>

</span>

</div>

</body>
</html>
</#macro>