<#list childEmailContacts as childKey, childContactList>
${childKey}
------------------------------------------------------------
<#list childContactList as contact>
${contact}
</#list>

</#list>

