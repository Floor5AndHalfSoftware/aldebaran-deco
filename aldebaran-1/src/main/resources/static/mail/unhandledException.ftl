<#import "errorLayout.ftl" as layout/>
<@layout.base>

<p>
	&egrave; stata rilevata una eccezione non gestita:<br><br><br>
	
	
	<h3><b>Informazioni generiche</b></h3>
	<#if isScheduledTask??>
		<b>Eccezione durante l'esecuzione di uno Scheduled Task</b><br>
	<#else>
		<b>Utente Loggato:</b> <#if request.getUserPrincipal()??>${request.getUserPrincipal().getName()}<#else>-</#if><br>
	
		<b>Indirizzo IP:</b> ${request.getRemoteAddr()}<br>
		<b>URI Richiesto:</b> ${request.getRequestURI()}<br>
		<b>User-Agent:</b> ${request.getHeader("User-Agent")}<br>
		<b>Protocollo:</b> ${request.getProtocol()}<br>
		<b>Metodo:</b> ${request.getMethod()}<br><br>
		
		<b>Parametri:</b><br>
		<#if parameters?size gt 0>
			<table>
				<#list parameters as parameter>
					<#assign parametersCount = 1>
					<#assign parameterValues = parameter.getValue()>
				    <tr>
						<td><pre>${parameter.getKey()}: </pre></td>
						<td><pre><#list parameterValues as parameterValue><#if parametersCount == 1>[</#if>${parameterValue}<#if parametersCount == parameterValues?size>]<#else>, </#if><#assign parametersCount=parametersCount+1></#list></pre></td>
					</tr>
				</#list>
			</table>
		<#else>
				Nessun parametro.
		</#if>
		<br><br>
		
		<b>Headers:</b><br>
		<table>
			<#list headerNames as headerName>
			    <tr>
					<td><pre>${headerName}:</pre></td>
					<td><pre>${request.getHeader(headerName)}</pre></td>
				</tr>
			</#list>
		</table>
		
		<#if isGraphQlRequest??>
			<h3><b>Richiesta GraphQL</b></h3>
			<b>GraphQL Endpoint:</b> ${graphQlEndpoint}<br><br>    	
	    	<table>
				<#list graphQlVariables as graphQlVariable>
					<tr>
						<td><pre>${graphQlVariable}</pre></td>
					</tr>
				</#list>
			</table>
		</#if>
		
		<h3><b>Info host remoto</b></h3>
		<b>Remote Address:</b> ${request.getRemoteAddr()}<br>
		<b>Remote Host:</b> ${request.getRemoteHost()}<br>
		<b>Remote Port:</b> ${request.getRemotePort()}<br><br><br>
	
		<h3><b>Info server</b></h3>
		<b>Local Address:</b> ${request.getLocalAddr()}<br>
		<b>Local Name:</b> ${request.getLocalName()}<br>
		<b>Local Port:</b> ${request.getLocalPort()}<br><br>
	</#if>
	
	
	<h3><b>Dettaglio Errore</b></h3>
	<b>Messaggio:</b> <#if exception.getMessage()??>${exception.getMessage()}<#else>-</#if><br><br>
	
	<b>StackTrace:</b><br> <pre>${stackTrace}</pre><br><br><br>
	
</p>

</@layout.base>