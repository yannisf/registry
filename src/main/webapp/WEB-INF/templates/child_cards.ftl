<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Καρτέλες παιδιού</title>
        <link rel="stylesheet" type="text/css" href="${css}"/>
    </head>

    <body>
        <table>
            <tbody>

                <tr>
                    <td colspan="2" class="simple">${child.simpleName}</td>
                </tr>

                <tr>
                    <td colspan="2" class="full${mod!''}">${child.informalFullName}</td>
                </tr>

                <tr>
                    <#list 0..1 as i>
                    <td class="tablet-small${mod!''}">
                        ${child.simpleName}<br/>
                        ${child.lastName}
                    </td>
                    </#list>
                </tr>

                <tr>
                    <#list 0..1 as i>
                    <td class="tablet-medium${mod!''}">
                        ${child.simpleName}<br/>
                        ${child.lastName}
                    </td>
                    </#list>
                </tr>

                <tr>
                    <td colspan="2" class="tablet-large${mod!''}">
                        ${child.simpleName}<br/>
                        ${child.lastName}
                    </td>
                </tr>

            </tbody>
        </table>
    </body>

</html>
