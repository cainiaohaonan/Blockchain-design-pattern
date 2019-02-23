//deployEthereum
function fileinit() {
    var name_difficult = document.getElementById("id_difficult").value;
    window.location.href = "../jsonFileServlet.do?difficultvalue="+name_difficult;
}
function deployinit() {
    window.location.href = "/ubaas/deployServlet.do";
}
//filehash
function hashfile(){
    window.location.href="../iframe/hashfile.jsp";
}
function comparefile(){
    window.location.href="../iframe/comparefile.jsp";
}
//encryption
function createSecret(){
	console.log("3");
    window.location.href="../iframe/createkey.jsp";
}


function toencrypted(){
	console.log("1");
	window.location.href="../iframe/encrypteddata.html";
}
function todecrypted(){
	console.log("2");
	window.location.href="../iframe/decrypteddata.html";
}
//statechannel
function toOK(){
    var tr_type = document.getElementById("tr_type").value;
    var tr_limit = document.getElementById("tr_limit").value;
    var num = document.getElementById("tx_id_num").value;
    console.log(tr_limit+","+tr_type+","+num);
    window.location.href="../storeStatechannel.do?tr_type="+tr_type+"&tr_limit="+tr_limit+"&limitnum="+num;
}
//datastore
var sqlCreateTable = "CREATE TABLE ";
var tableName = "";
var sqlStart = "("
var sqlEnd = ")";

function createtable(){
    window.location.href="../createTableServlet.do?sqlCreateTable="+sqlCreateTable;
}
function writetabelname(){
    var tablename = document.getElementById("tx_id_tablename").value;
    var showSql = document.getElementById("tx_id_showSql");
    sqlCreateTable = sqlCreateTable + tablename + sqlStart;
    showSql.value = sqlCreateTable + sqlEnd;
}
function isok(){
    var showSql = document.getElementById("tx_id_showSql");
    var attribute = document.getElementById("tx_id_attribute").value;
    var attributeType = document.getElementById("attributeType").value;
    var isNull = document.getElementsByName("name_isnull");
    var isPrimaryKey = document.getElementsByName("name_isprimaryKey");
    var isImportmance = document.getElementsByName("name_importance");
    var selectvalue1="";   //  selectvalue为radio中选中的值
    var selectvalue2="";   //  selectvalue为radio中选中的值
    var selectvalue3="";   //  selectvalue为radio中选中的值
    for(var i=0;i<isNull.length;i++){//是否为空
        if(isNull[i].checked==true) {
            selectvalue1 = selectvalue1 + isNull[i].value;
        }
    }
    console.log("selectvalue1" + selectvalue1);
    for(var i=0;i<isPrimaryKey.length;i++){//是否为主键
        if(isPrimaryKey[i].checked==true) {
            selectvalue3 = selectvalue3 + isPrimaryKey[i].value;
        }
    }
    console.log("selectvalue1" + selectvalue1);
    for(var i=0;i<isImportmance.length;i++){//是否重要
        if(isImportmance[i].checked == true) {
            selectvalue2 = selectvalue2 + isImportmance[i].value;
        }
    }
    console.log("selectvalue2" + selectvalue2);
    if(selectvalue2 == "no"){
        if(selectvalue1 == "yes"){
            sqlCreateTable = sqlCreateTable + attribute + " " + attributeType + ",";
        }else{
            if(selectvalue3 == "yes"){
                sqlCreateTable = sqlCreateTable + attribute + " " + attributeType + " primary key,";
            }else{
                sqlCreateTable = sqlCreateTable + attribute + " " + attributeType + " not null,";
            }
        }

    }else{

    }
    showSql.value = sqlCreateTable + sqlEnd;
}