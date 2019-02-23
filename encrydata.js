function encrydata(){
	var edata = document.getElementById("tx_id_data").value;
	window.location.href="../encryptedata.do?edata="+edata;
}
function back(){
	window.location.href="../iframe/Encryption.html";
}

