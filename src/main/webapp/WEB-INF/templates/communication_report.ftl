<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>Πίνακας Επικοινωνίας</title>
    <link rel="stylesheet" type="text/css" href="${css}"/>
    <#if format=="a3">
        <style type="text/css">
            @page {
                size: A3;
            }
        </style>
    </#if>
</head>

<body>

    <div id="header">
        <div style="float: left">
            <span>${schoolData.school}</span><br/>
            <span>Τμήμα: ${schoolData.department}</span>
        </div>

        <div style="float: right">
            <span>Τάξη ${schoolData.group}</span>
        </div>
    </div>

    <div id="footer">
        <div style="float: right">
            <span id="pagenumber"></span>/<span id="pagecount"></span>
        </div>
    </div>


    <h1>Πίνακας Επικοινωνίας</h1>
    <table>
        <thead>
            <tr>
                <th class="col1">A/A</th>
                <th class="col2">Ονοματεπώνυμο</th>
                <th class="col3">Επικοινωνία/Αποχώρηση</th>
                <th class="col4">Παρατηρήσεις</th>
            </tr>
        </thead>

        <tbody>
            <#list children as child>
            <tr>
                <th>${child?counter}</th>
                <td class="name">${child.name}</td>
                <td>
                    <ul>
                        <#if (child.guardians)??>
                            <#list child.guardians as guardian>
                                <li>
                                    ${guardian.name}
                                    <strong style="font-size:5pt">${relationshipTypeMap[guardian.relationship]}</strong>
                                    <#if (guardian.telephones)??>
                                        <#list guardian.telephones as telephone>
                                            |${telephone.number} <strong style="font-size:5pt">${phoneTypeMap[telephone.type]}</strong>
                                        </#list>
                                    </#if>
                                </li>
                            </#list>
                        </#if>
                    </ul>
                </td>
                <td class="notes">${child.notes!''}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>

</html>
