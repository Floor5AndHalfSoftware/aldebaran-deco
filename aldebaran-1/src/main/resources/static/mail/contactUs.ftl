<#import "defaultLayout.ftl" as layout/>
<@layout.base>

<p>
	${name} (Document id: ${cedula}) writes to us:
</p>
<p>
	<i>
		"${comments}"
	</i>
</p>

</@layout.base>