document.write("<script language=javascript src='/ubaas/web3/dist/web3.js'><\/script>");
document.write("<script language=javascript src='/ubaas/web3/dist/web3.min.js'><\/script>");
document.write("<script type='text/javascript' src='/ubaas/js/jquery.min.js'></script>");

var sqlCreateTable = "CREATE TABLE ";
var tableName = "";
var sqlStart = "(";
var sqlEnd = ")";
var createContract = "contract ";
var contractName = "";
var cStart = "{";
var cEnd = "}";
var onchainattr = "";
var offchainattr = "";
var onchainattrtype = "";
var offchainattrtype = "";
var pkey = "";//主键
function selectDB(){
	var sel = document.getElementById('id_database');
	$.ajax({
		method: 'post',
		url:'/ubaas/createDB.do',
		dataType: "text",
		data:{},
		success:function(json){
			var jsonObject = eval("("+json+")");
			
			var dbname = jsonObject[0].listdbname;
			var dbnamepb = jsonObject[0].listpb;
			console.log(dbname+":::"+dbnamepb);
			for (var i=0;i < dbname.length; i++) {
				//sel.options.add(new Option(arr[i],i));
				if(dbnamepb[i] == "0"){
					var op = document.createElement('OPTION');
					sel.appendChild(op);
					op.text = dbname[i];
					op.value = dbname[i];
				}
			}
		}
	});
}
function createtable(){//创建数据库表，以及存储表中属性
	console.log("进入成功s");
	var dbname = document.getElementById("id_database").value;
	var tablename = document.getElementById("tx_id_tablename").value;//获取SQL语句
	var contractString = document.getElementById("txta_id_contract").value;//获取合约代码
	deployContract(contractName,contractString);//部署合约
	console.log("onchainattr="+onchainattr);
	console.log("onchainattrtype="+onchainattrtype);
	console.log("offchainattr="+offchainattr);
	console.log("offchainattrtype="+offchainattrtype);
	
	window.location.href="../createTableServlet.do?sqlCreateTable="+
	sqlCreateTable+"&onchainattr="+onchainattr+"&onchainattrtype="+onchainattrtype+
	"&offchainattr="+offchainattr+"&offchainattrtype="+offchainattrtype+"&tablename="+tablename+"&pkey="+pkey+"&dbname="+dbname;//链下创建表以及存储表属性
}
function writetabelname(){//创建表名以及合约名字
	var tablename = document.getElementById("tx_id_tablename").value;//获取表名
	contractName = document.getElementById("tx_id_tablename").value;//设置合约名字
	var contractText = document.getElementById("txta_id_contract");
	var showSql = document.getElementById("tx_id_showSql");
	sqlCreateTable = sqlCreateTable + tablename + sqlStart;
	showSql.value = sqlCreateTable + sqlEnd;
	//createContract = createContract + tablename + cStart;
	contractText.value = createContract + cEnd;
	
	createContract = createContract + tablename + cStart + "\n mapping(string => string) " +contractName+"attribute;\n"
	+"function set(string attrname,string value){\n"+contractName+"attribute[attrname] = value;}\n"
	+"function get(string attrname) constant returns(string){\n return "+contractName+"attribute[attrname];\n}\n";//相对应的合约代码
	
}
function isok(){//确认一个属性，往SQL语句中添加一个属性
	var showSql = document.getElementById("tx_id_showSql");
	var contractText = document.getElementById("txta_id_contract");
	var attribute = document.getElementById("tx_id_attribute").value;
	var attributeType = document.getElementById("attributeType").value;
	var isNull = document.getElementsByName("name_isnull");
	var isPrimaryKey = document.getElementsByName("name_isprimaryKey");
	var isImportmance = document.getElementsByName("name_importance");
	var selectvalue1="";   //  selectvalue为radio中选中的值
	var selectvalue2="";   //  selectvalue为radio中选中的值
	var selectvalue3="";   //  selectvalue为radio中选中的值
	
	for(var i=0;i<isNull.length;i++){//属性是否为能为空
		if(isNull[i].checked==true) {
			selectvalue1 = selectvalue1 + isNull[i].value; 
		}
	}
	console.log("selectvalue1" + selectvalue1);
	
	for(var i=0;i<isPrimaryKey.length;i++){//此属性是否设置为主键
		if(isPrimaryKey[i].checked==true) {
			selectvalue3 = selectvalue3 + isPrimaryKey[i].value; //把属性添加到SQL语句中
		}
	}
	console.log("selectvalue1" + selectvalue1);
	
	for(var i=0;i<isImportmance.length;i++){//是否存储到链上
		if(isImportmance[i].checked == true) {
			selectvalue2 = selectvalue2 + isImportmance[i].value; //把属性添加到SQL语句中
		}
	}
	console.log("selectvalue2" + selectvalue2);
	

	if(selectvalue1 == "yes"){
		sqlCreateTable = sqlCreateTable + attribute + " " + attributeType + ","; //把属性添加到SQL语句中
	}else{
		if(selectvalue3 == "yes"){
			pkey = attribute ;
			sqlCreateTable = sqlCreateTable + attribute + " " + attributeType + " not null primary key,"; //把属性添加到SQL语句中
		}else{
			sqlCreateTable = sqlCreateTable + attribute + " " + attributeType + " not null,"; //把属性添加到SQL语句中
		}
	}
	
	if(selectvalue2 == "no"){//不存储到链上
		offchainattr =offchainattr+attribute+",";//记录链下属性
		offchainattrtype =offchainattrtype +attributeType+",";//记录链下属性类型
	}else{
		onchainattr = onchainattr+attribute+",";
		onchainattrtype =onchainattrtype + attributeType+",";
		contractText.value = createContract+cEnd;
	}
	showSql.value = sqlCreateTable + sqlEnd;//组合成完整的SQL语句
	
}
function deployContract(contractName1, src){
	var dbname = document.getElementById("id_database").value;
	if (src != null && ""!=(src)) {
		while (src.indexOf("&lt;") != -1) {
		src = src.substring(0, src.indexOf("&lt;")) + "<"
		+ src.substring(src.indexOf("&lt;") + 1);
		}
		while (src.indexOf("&gt;") != -1) {
		src = src.substring(0, src.indexOf("&gt;")) + ">"
		+ src.substring(src.indexOf("&gt;") + 4);
		}
		/*while (str.indexOf(" ") != -1) {
		str = str.substring(0, str.indexOf(" ")) + "&nbsp;"
		+ str.substring(str.indexOf(" ") + 1);
		}*/
		}
	//console.log(src);
	
	var Web3 = require('web3');
    var web3 = new Web3();
    web3.setProvider(new web3.providers.HttpProvider("http://localhost:8545"));
    
    var stdin = "<stdin>:"+contractName1;
    
    var compiled = web3.eth.compile.solidity(src);
    var contractInfo = compiled[stdin];
    
    var code = contractInfo["code"];
    var abi = contractInfo["info"]["abiDefinition"];
    
    var preBlock = web3.eth.blockNumber;
    
    var contract = web3.eth.contract(abi);
    
    var deployed;
    contract.new({data: code,from: web3.eth.coinbase, gas: 1000000}, function (err, contract){
    	if(!err) {

    		if(!contract.address) {
    			  console.log("Contract transaction send: TransactionHash: " + contract.transactionHash + " waiting to be mined...");
    		  }else {
    			  deployed = contract;
    			  console.log('address: ' + deployed.address);
    			  abi = JSON.stringify(abi);
    			//链下数据库中有一个deployedContract表，我们把合约的abi、名字、地址、发布人、发布时间(在servlet中生成)、交易hash、源码都保存下来
    			
    			  $.ajax({
    					method: 'post',
    					url:'../contractDeployServlet?action=deployinfo',
    					dataType: "text",
    					data:{'dbname':dbname,'contractName':contractName1,'abi':abi,'address':deployed.address,'client':web3.eth.coinbase,'src':src,'txHash':deployed.transactionHash},
    					success:function(){
    			   		    //alert("","Saved!");
    			    	//window.location.reload();
    			    		//clearForm();
    					}
    				});
    				
    	}}
    	else
    		console.log(err);
    });  
} 