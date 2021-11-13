package com.dhj.eac_api.service;

import com.dhj.eac_api.exception.JSONException;
import com.dhj.eac_api.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ApiService {
    private final RpcClient client;
    private final String errResp = "request err";
    private static final DecimalFormat DF = new DecimalFormat("#.#");
    private static final DecimalFormat DF8 = new DecimalFormat("#.########");

    private static final String DEFAULT_THREAD_NAME = "API response task";
    private static final String UNKNOWN_API_METHOD = "{\"Error\": \"Unknown API method\"}\n";
    private static final String RESPONSE_HEADER =
            "HTTP/1.1 200 OK\n" +
                    "Access-Control-Allow-Origin: *\n" +
                    "Access-Control-Request-Method: *\n" +
                    "Access-Control-Allow-Methods: GET\n" +
                    "Access-Control-Allow-Headers: *\n" +
                    "Content-Type: application/json; charset=UTF-8\n" +
                    "X-Powered-By: sando\n" +
                    "Connection: close\n";
    private static final String HTTP_HEADER =
            "HTTP/1.1 200 OK\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Connection: close\n\n";
    private static final String EXPLORER_HEADER =
            "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "<title>EarthCoin (EAC) API & Block Explorer</title>\n"
                    + "<style>a:link, a:visited {text-decoration: none; color: blue}</style>\n</head>\n"
                    + "<body><h1><p align=\"center\"><a href=\"/explorer\">API & Block Explorer for EarthCoin</a></p></h1>"
                    + "<table width=\"100%\"><tr><td width=\"20%\">&nbsp;&nbsp;"
                    + "</td><td width=\"60%\">"
                    + "<div align=\"center\"> "
                    + "<form action=\"/search/\" method=\"get\">"
                    + "<input name=\"q\" type=\"text\" size=\"80\" placeholder=\"block hash, index, transaction or address\" />"
                    + "<input type=\"submit\" value=\"Search\" /></form></div>"
                    + "</td><td width=\"20%\">"
                    + "<a href=\"/doc\">API documentation</a>"
                    + "</td></tr></table><hr>";
    private static final String EXPLORER_FOOTER =
            "<hr><p align=\"center\">Powered by <A href=\"https://github.com/Sandokaaan/EAC_API_JAVA.git\">"
                    + "Sando-explorer v.2.0</A> &nbsp; &#9400; 2019</P></body></html>";

    public ApiService(RpcClient client) {
        this.client = client;
    }

    public String resp(String[] params) {
        String respVal;
        switch (params[1]) {
            case "getinfo":
            case "getblockchaininfo":
                respVal = getinfo();
                break;
            case "getpeerinfo":
                respVal = getpeerinfo();
                break;
            case "getdifficulty":
                respVal = getdifficulty();
                break;
            case "getblockcount":
            case "getheight":
                respVal = getblockcount();
                break;
            case "getblockhash":
                respVal = getblockhash(params);
                break;
            case "getbestblockhash":
                respVal = getbestblockhash();
                break;
            case "getblock":
            case "block":
                respVal = getblock(params);
                break;
            case "getrawtransaction":
            case "extx":
            case "tx":
                respVal = getrawtransaction(params);
                break;
            case "gettxout":
                respVal = gettxout(params);
                break;
            case "sendrawtransaction":
                respVal = sendrawtransaction(params);
                break;
            case "sendcoins":
                respVal = sendCoins(params);
                break;
            case "validateaddress":
                respVal = validateaddress(params);
                break;
            case "getnetworkhashps":
                respVal = getnetworkhashps();
                break;
            case "utxo":
            case "unspent":
                respVal = scantxoutset(params);
                break;
            case "getbalance":
                respVal = getbalance(params);
                break;
            case "check":
            case "checktransaction":
                respVal = checktransaction(params);
                break;
            case "explorer":
                respVal = explore(params);
                break;
            case "transaction":
                respVal = transaction(params);
                break;
            case "addressinfo":
                respVal = addressinfo(params);
                break;
            case "balance":
                respVal = balance(params);
                break;
            case "search":
                respVal = search(params);
                break;
            default:
                respVal = "request err";
        }
        return respVal;
    }

    private String getinfo() {
        return client.query("getblockchaininfo");
    }

    private String getdifficulty() {
        return client.query("getdifficulty");
    }

    private String getblockcount() {
        return client.query("getblockcount");
    }

    private String addStringQuotes(String s) {
        return (s.startsWith("{")) ? s : "\"" + s + "\"";
    }

    private String getblockhash(String[] params) {
        try {
            if ((params.length < 3) || (params[2].length() == 0)) {
                throw (new NumberFormatException("Missing parameter"));
            }
            int index = Integer.parseInt(params[2]);
            return addStringQuotes(client.query("getblockhash", index));
        } catch (NumberFormatException ex) {
            return "{\"Error\": \"API method requires an integer parameter\"}\n";
        }
    }

    private String getbestblockhash() {
        return addStringQuotes(client.query("getbestblockhash"));
    }

    private String getblock(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires an parameter\"}\n";
        }
        int verbosity = 1;
        if (params.length == 4) {
            try {
                verbosity = Integer.parseInt(params[3]);
                if ((verbosity < 0) || (verbosity > 2)) {
                    verbosity = 1;
                }
            } catch (NumberFormatException ex) {
                verbosity = 1;
            }
        }
        String hash = params[2];
        String rts = client.query("getblock", hash, verbosity);
        return (verbosity == 0) ? addStringQuotes(rts) : rts;
    }

    private double round8(double value) {
        return ((double) ((long) (value * 100000000))) / 100000000;
    }

    private String getrawtransaction(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires an parameter\"}\n";
        }
        String hash = params[2];
        int decrypt = 0;
        if (params.length >= 4) {
            if ("true".equals(params[3]) || "1".equals(params[3])) {
                decrypt = 1;
            }
        }
        if ("tx".equals(params[1]) || "extx".equals(params[1])) {
            decrypt = 1;
        }
        String rts = (client.query("getrawtransaction", hash, decrypt));
        if ("extx".equals(params[1])) {
            try {
                double txValue = 0.0;
                JSONObject jsonTx = new JSONObject(rts);
                JSONArray jsonOutputs = jsonTx.getJSONArray("vout");
                for (int i = 0; i < jsonOutputs.length(); i++) {
                    JSONObject jsonOut = jsonOutputs.getJSONObject(i);
                    txValue += jsonOut.getDouble("value");
                }
                double txFee = -txValue;
                JSONArray jsonInputs = jsonTx.getJSONArray("vin");
                for (int j = 0; j < jsonInputs.length(); j++) {
                    JSONObject jsonIn = jsonInputs.getJSONObject(j);
                    if (jsonIn.has("txid")) {
                        String srcTx = jsonIn.getString("txid");
                        int i = jsonIn.getInt("vout");
                        String subquery = (client.query("getrawtransaction", srcTx, decrypt));
                        JSONObject jsonSrcTx = new JSONObject(subquery);
                        JSONObject jsonOut = jsonSrcTx.getJSONArray("vout").getJSONObject(i);
                        String address = jsonOut.getJSONObject("scriptPubKey").getJSONArray("addresses").getString(0);
                        double value = jsonOut.getDouble("value");
                        txFee += value;
                        jsonIn.put("address", address);
                        jsonIn.put("value", value);
                        jsonInputs.put(j, jsonIn);
                    }
                }
                jsonTx.put("vin", jsonInputs);
                jsonTx.put("txValue", round8(txValue));
                jsonTx.put("txFee", round8(txFee > 0 ? txFee : 0));
                rts = jsonTx.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return addStringQuotes(rts);
    }

    private String gettxout(String[] params) {
        try {
            if ((params.length < 4) || (params[2].length() == 0) || (params[3].length() == 0)) {
                throw (new NumberFormatException("Missing parameter"));
            }
            int index = Integer.parseInt(params[3]);
            String txhash = params[2];
            return client.query("gettxout", txhash, index);
        } catch (NumberFormatException ex) {
            return "{\"Error\": \"API method requires two parameters\"}\n";
        }
    }

    private String sendrawtransaction(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires an parameter\"}\n";
        }
        String txdata = params[2];
        return addStringQuotes(client.query("sendrawtransaction", txdata));
    }

    private String validateaddress(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires an parameter\"}\n";
        }
        String addr = params[2];
        return client.query("validateaddress", addr);
    }

    private String getnetworkhashps() {
        return client.query("getnetworkhashps");
    }

    private String scantxoutset(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires at least one parameter\"}\n";
        }
        int n = params.length - 2;
        String[] scanobjects = new String[n];
        for (int i = 0; i < n; i++) {
            scanobjects[i] = "addr(" + params[i + 2] + ")";
        }
        return client.query("scantxoutset", "start", scanobjects);
    }

    private String getbalance(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires at least one parameter\"}\n";
        }
        JSONObject jsonUnspent = getUnspentBalanceInternal(params[2]);
        double balance = jsonUnspent.optDouble("total_amount", 0);
        return "{\"Balance\": " + balance + "}\n";
    }


    private String getpeerinfo() {
        return client.query("getpeerinfo");
    }

    private boolean testOutputs(JSONArray jsonOuts, String address, double txValue) {
        for (int i = 0; i < jsonOuts.length(); i++) {
            JSONArray arresses = jsonOuts.getJSONObject(i).getJSONObject("scriptPubKey").getJSONArray("addresses");
            double sentValue = jsonOuts.getJSONObject(i).getDouble("value");
            if (sentValue >= txValue) {
                for (Object adr : arresses) {
                    if (address.equals(adr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean testTxComment(String txCommemt, JSONObject jsonTx) {
        boolean txCommentPass = true;
        if (txCommemt.length() > 0) {
            txCommentPass = jsonTx.has("txComment") && txCommemt.equals(jsonTx.getString("txComment"));
        }
        return txCommentPass;
    }

    @SuppressWarnings("UseSpecificCatch")
    private String checktransaction(String[] params) {
        if ((params.length < 3) || (params[2].length() == 0)) {
            return "{\"Error\": \"API method requires some parameters, at least an address\"}\n";
        }
        String address = params[2];
        double txValue = Utils.passDoubleParam(params, 3, 0);
        String txCommemt = Utils.passStringParam(params, 4);
        int orderHeight = Utils.passIntParam(params, 5, 0);
        int expirationTime = Utils.passIntParam(params, 6, 60);
        if (orderHeight == 0) {
            orderHeight = Utils.passIntParam(new String[]{client.query("getblockcount")}, 0, 60) - 60;
        }
        try {
            // scan the memory pool first
            String rts = client.query("getrawmempool");
            JSONArray jsonMemPool = new JSONArray(rts);
            for (Object o : jsonMemPool) {
                String tx = client.query("getrawtransaction", o, 1);
                JSONObject jsonTx = new JSONObject(tx);
                JSONArray jsonOuts = jsonTx.getJSONArray("vout");
                if (testTxComment(txCommemt, jsonTx)
                        && testOutputs(jsonOuts, address, txValue)) {
                    jsonTx.put("confirmations", 0);
                    return jsonTx.toString();
                }
            }
            if (orderHeight > 0) {
                double orderTimeLimit = 0;
                // scan the blockchain
                String blockHash = client.query("getblockhash", orderHeight);
                while (true) {
                    String block = client.query("getblock", blockHash, 2);
                    JSONObject jsonBlock = new JSONObject(block);
                    double blockTime = jsonBlock.getDouble("time");
                    if (orderTimeLimit == 0) {
                        orderTimeLimit = blockTime + 60 * expirationTime;
                    }
                    JSONArray jsonTxs = jsonBlock.getJSONArray("tx");
                    for (int j = 1; j < jsonTxs.length(); j++) {
                        JSONObject rtsTx = jsonTxs.getJSONObject(j);
                        JSONArray jsonOuts = rtsTx.getJSONArray("vout");
                        if (testTxComment(txCommemt, rtsTx)
                                && testOutputs(jsonOuts, address, txValue)) {
                            rtsTx.put("confirmations", jsonBlock.getInt("confirmations"));
                            return rtsTx.toString();
                        }
                    }
                    if ((!(jsonBlock.has("nextblockhash"))) || (blockTime > orderTimeLimit)) {
                        break;
                    }
                    blockHash = jsonBlock.getString("nextblockhash");
                }
            }
            return "{\"Error\": \"Payment not found\"}\n";
        } catch (Exception ex) {
            return "{\"Error\": \"An internal error. Please, contact the API operator\"}\n";
        }
    }


    private String explore(String[] params) {
        int blockIndex = -1;
        String blockHash = null;
        if ((params.length == 3) && (params[2].length() > 0)) {
            try {
                blockIndex = Integer.parseInt(params[2]);
            } catch (NumberFormatException ex) {
                blockHash = params[2];
            }
        }
        if (blockHash == null) {
            try {
                blockHash = (blockIndex < 0)
                        ? client.query("getbestblockhash")
                        : client.query("getblockhash", blockIndex);
            } catch (Exception ex) {
                return errResp;
            }
        }
        JSONObject json;
        try {
            String block = client.query("getblock", blockHash, 2);
            json = new JSONObject(block);
        } catch (JSONException ex) {
            return errResp;
        }
        String prevHash, nextHash, prevLink, nextLink;
        try {
            prevHash = json.getString("previousblockhash");
            prevLink = "<A href=\"/explorer/" + prevHash + "\"><B>&lt;&lt;</B></A>";
        } catch (JSONException ex) {
            prevLink = "<FONT color=\"gray\"><B>&lt;&lt;</B></FONT>";
        }
        try {
            nextHash = json.getString("nextblockhash");
            nextLink = "<A href=\"/explorer/" + nextHash + "\"> <B>&gt;&gt;</B></A>";
        } catch (JSONException ex) {
            nextLink = "<FONT color=\"gray\"><B>&gt;&gt;</B></FONT>";
        }
        try {
            int height = json.getNumber("height").intValue();
            String hash = json.getString("hash");
            int nTx = json.getNumber("nTx").intValue();
            int size = json.getNumber("size").intValue();
            int confirmations = json.getInt("confirmations");
            long time = json.getNumber("time").intValue();
            String formatedTime =
                    DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("GMT")).format(Instant.ofEpochSecond(time));
            double difficulty = json.getDouble("difficulty");
            String sSize = DF.format(((0.0 + size) / 1024)) + " kB";
            long now = Instant.now().toEpochMilli() / 1000;
            double age = (0.0 + now - time) / 60;
            String sAge = DF.format(age);
            JSONArray txArray = json.getJSONArray("tx");
            JSONObject[] jsonTx = new JSONObject[nTx];
            double[] valuesSent = new double[nTx];
            int[] coutOfSources = new int[nTx];
            int[] coutOfTargets = new int[nTx];
            String[] targetAddresses = new String[nTx];
            String[] targetValues = new String[nTx];
            double totalSent = 0.0;
            for (int i = 0; i < nTx; i++) {
                targetValues[i] = "";
                targetAddresses[i] = "";
                valuesSent[i] = 0.0;
                jsonTx[i] = txArray.getJSONObject(i);
                JSONArray coinBaseOutputs = jsonTx[i].getJSONArray("vout");
                coutOfSources[i] = jsonTx[i].getJSONArray("vin").length();
                coutOfTargets[i] = coinBaseOutputs.length();
                for (int j = 0; j < coutOfTargets[i]; j++) {
                    JSONObject jsonOut = coinBaseOutputs.getJSONObject(j);
                    String address;
                    try {
                        address = jsonOut.getJSONObject("scriptPubKey").getJSONArray("addresses").getString(0);
                    } catch (JSONException ex) {
                        address = "null";
                    }
                    double value = jsonOut.getDouble("value");
                    targetValues[i] += DF8.format(value) + "<BR>";
                    targetAddresses[i] += "<a href=\"/addressinfo/" + address + "\">" + address + "</a><BR>";
                    totalSent += value;
                    valuesSent[i] += value;
                }
            }
            StringBuilder rts = new StringBuilder(EXPLORER_HEADER
                    + "<H3>Details of block <code>" + height + "</code> </H1><HR>"
                    + "<code><table><tr>"
                    + "<td> Hash </td>"
                    + "<td align=\"right\"> " + prevLink + " </td>"
                    + "<td> " + hash + " </td>"
                    + "<td align=\"left\"> " + nextLink + " </td></tr>"
                    + "<tr><td>Height</td><td>&nbsp;</td><td>" + height + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Confirmations</td><td>&nbsp;</td><td>" + confirmations + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Timestamp</td><td>&nbsp;</td><td>" + time + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Date/Time</td><td>&nbsp;</td><td>" + formatedTime + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Age</td><td>&nbsp;</td><td>" + sAge + " min</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Count of transactions</td><td>&nbsp;</td><td>" + nTx + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Block size</td><td>&nbsp;</td><td>" + sSize + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Difficulty</td><td>&nbsp;</td><td>" + DF8.format(difficulty) + "</td><td>&nbsp;</td></tr>"
                    + "<tr><td>Block reward</td><td>&nbsp;</td><td>" + DF8.format(valuesSent[0]) + " EAC </td><td>&nbsp;</td></tr>"
                    + "<tr><td>Total output</td><td>&nbsp;</td><td>" + DF8.format(totalSent) + " EAC </td><td>&nbsp;</td></tr>"
                    + "</table></code><br><br>Transactions<br>"
                    + "<code><table width=\"100%\" border=\"1\"><tr><td width=\"5%\" align=\"center\">#</td>"
                    + "<td width=\"45%\" align=\"center\">TxID</td>"
                    + "<td width=\"10%\" align=\"center\">Value Out (EAC)</td>"
                    + "<td width=\"10%\" align=\"center\">Count of sources</td>"
                    + "<td width=\"20%\" align=\"center\">To</td>"
                    + "<td width=\"10%\" align=\"center\">Amount (EAC)</td</tr><tr>");
            for (int i = 0; i < nTx; i++) {
                String sTx = jsonTx[i].getString("txid");
                rts.append("<td align=\"center\">")
                        .append(i)
                        .append("</td>")
                        .append("<td align=\"center\"><a href=\"/transaction/")
                        .append(sTx)
                        .append("\">")
                        .append(sTx)
                        .append("</a></td>")
                        .append("<td align=\"center\">")
                        .append(DF8.format(valuesSent[i]))
                        .append("</td>")
                        .append("<td align=\"center\">")
                        .append(coutOfSources[i])
                        .append("</td>")
                        .append("<td align=\"center\">")
                        .append(targetAddresses[i])
                        .append("</td>")
                        .append("<td align=\"center\">")
                        .append(targetValues[i])
                        .append("</td></tr>");
            }
            rts.append("</table></code>" + EXPLORER_FOOTER);
            return rts.toString();
        } catch (JSONException e) {
            return errResp;
        }
    }

    private String transaction(String tx) {
        String txid, blockhash;
        int confirmations, size, version;
        StringBuilder inputs;
        StringBuilder outputs;
        long timestamp;
        JSONArray vin, vout;
        double txFee, txValue;
        try {
            inputs = new StringBuilder("<TABLE>");
            outputs = new StringBuilder("<TABLE>");
            JSONObject json = new JSONObject(tx);
            txid = json.getString("txid");
            blockhash = json.getString("blockhash");
            confirmations = json.getInt("confirmations");
            version = json.getInt("version");
            size = json.getInt("size");
            timestamp = json.getLong("time");
            vin = json.getJSONArray("vin");
            vout = json.getJSONArray("vout");
            txFee = json.optDouble("txFee");
            txValue = json.optDouble("txValue");
            for (int i = 0; i < vin.length(); i++) {
                JSONObject jsonVin = vin.getJSONObject(i);
                if (jsonVin.has("coinbase")) {
                    inputs.append("<tr><td>").append(i).append("</td><td>&nbsp;</td><td>coinbase</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
                } else {
                    String prevTx = jsonVin.getString("txid");
                    int prevVout = jsonVin.getInt("vout");
                    inputs.append("<tr><td>").append(i).append("</td><td>&nbsp;</td><td>txid: <A href=\"/transaction/").append(prevTx).append("\">").append(prevTx).append("</a></td><td>&nbsp;</td><td>vout: ").append(prevVout).append("</td><tr>");
                    try {
                        String srcAddress = jsonVin.getString("address");
                        double value = jsonVin.getDouble("value");
                        inputs.append("<tr><td>\t</td><td>&nbsp;</td><td>address: <A href=\"/addressinfo/").append(srcAddress).append("\">").append(srcAddress).append("</a></td><td>&nbsp;</td><td>value: ").append(round8(value)).append(" EAC</td><tr>");
                    } catch (Exception ex) {
                        inputs.append("<tr><td>\t</td><td>&nbsp;</td><td>hidden source address</td><td>&nbsp;</td><td>&nbsp;</td><tr>");
                    }
                }
            }
            inputs.append("</TABLE>");
            for (int i = 0; i < vout.length(); i++) {
                JSONObject jsonVout = vout.getJSONObject(i);
                JSONArray addresses = jsonVout.getJSONObject("scriptPubKey").optJSONArray("addresses");
                String address;
                if (addresses != null) {
                    address = addresses.optString(0, "null");
                } else {
                    address = "null";
                }
                String linkedAddr = "<a href=\"/addressinfo/" + address + "\">" + address + "</a>";
                double value = jsonVout.getDouble("value");
                outputs.append("<tr><td>").append(i).append("</td><td>&nbsp;</td><td>").append(linkedAddr).append("</td><td>&nbsp;</td><td> value: ").append(DF8.format(value)).append("</td></tr>");
            }
            outputs.append("</TABLE>");
            if (version == 2) {
                String txMessage = json.optString("txComment");
                if (txMessage.length() > 0) {
                    outputs.append("</br><font color=\"red\"><b>Transaction message: </b></font>").append(txMessage).append("</br>");
                }
                String IPFS_CID = json.optString("IPFS_CID");
                if (IPFS_CID.length() > 0) {
                    outputs.append("<form action=\"https://ipfs.io/ipfs/").append(IPFS_CID).append("\">").append(" <font color=\"red\"><b>IPFS_CID: </b></font>").append(IPFS_CID).append(" <input style=\"border-radius: 12px;\" type=\"submit\" formtarget=\"_blank\" value=\"View on ipfs.io\" />").append(" <button style=\"border-radius: 12px;\" type=\"submit\" formtarget=\"_blank\" formaction=\"https://gateway.pinata.cloud/ipfs/").append(IPFS_CID).append("\">View on pinata.cloud</button>").append(" <button style=\"border-radius: 12px;\" type=\"submit\" formtarget=\"_blank\" formaction=\"https://dweb.link/ipfs/").append(IPFS_CID).append("\">View on dweb.link</button>").append(" <button style=\"border-radius: 12px;\" type=\"submit\" formtarget=\"_blank\" formaction=\"https://ipfs.fleek.co/ipfs/").append(IPFS_CID).append("\">View on fleek.co</button>").append("</form><br>");
                }
            }
        } catch (JSONException ex) {
            return errResp;
        }
        String formatedTime =
                DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("GMT")).format(Instant.ofEpochSecond(timestamp));
        String rts =
                EXPLORER_HEADER
                        + "<H3>Details of transaction <code>" + txid + "</code> </H1><HR><code><table>"
                        + "<tr><td>txid</td><td>&nbsp;</td><td>" + txid + "</td></tr>"
                        + "<tr><td>confirmations</td><td>&nbsp;</td><td>" + confirmations + "</td></tr>"
                        + "<tr><td>blockhash</td><td>&nbsp;</td><td><A href=\"/explorer/" + blockhash + "\">" + blockhash + "</A></td></tr>"
                        + "<tr><td>size</td><td>&nbsp;</td><td>" + size + "</td></tr>"
                        + "<tr><td>version</td><td>&nbsp;</td><td>" + version + "</td></tr>"
                        + "<tr><td>timestamp</td><td>&nbsp;</td><td>" + timestamp + "</td></tr>"
                        + "<tr><td>date/time</td><td>&nbsp;</td><td>" + formatedTime + "</td></tr>";
        if (!Double.isNaN(txValue)) {
            rts += "<tr><td>value</td><td>&nbsp;</td><td>" + DF8.format(txValue) + " EAC </td></tr>";
        }
        if (!Double.isNaN(txFee)) {
            rts += "<tr><td>fee</td><td>&nbsp;</td><td>" + DF8.format(txFee) + " EAC </td></tr>";
        }
        rts += "</table><br><B>inputs</B><BR>" + inputs + "<br>"
                + "<B>outputs</B><BR>" + outputs + "<br> </code>"
                + EXPLORER_FOOTER;
        return rts;
    }

    private String transaction(String[] params) {
        if ((params.length >= 3) && (params[2].length() > 0)) {
            try {
                params[1] = "extx";
                return transaction(getrawtransaction(params));
            } catch (Exception ex) {
                return "request err";
            }
        }
        return "request err";
    }

    private String addressinfo(String[] params) {
        if ((params.length >= 3) && (params[2].length() > 0)) {
            try {
                String addrInfo = client.query("validateaddress", params[2]);
                return addressinfo(addrInfo, params[2]);
            } catch (Exception ex) {
                return "request err";
            }
        }
        return "request err";
    }

    private String addressinfo(String addrInfo, String address) {
        String stripedInfo = addrInfo.replace("\"", "").replace("{", "").replace("}", "").replace(":", " ").replace(",", "<br>");
        JSONObject json = new JSONObject(addrInfo);
        String balanceLink;
        try {
            boolean isvalid = json.getBoolean("isvalid");
            if (isvalid) {
                balanceLink = "<A href=\"/balance/" + address + "\">Check the balance at this address</A>";
            } else {
                throw new JSONException("Invalid address");
            }
        } catch (JSONException ex) {
            balanceLink = "<B><FONT color=\"red\">This is not a valid EAC address.</FONT></B>";
        }
        return EXPLORER_HEADER
                + "<H3>Details of address <code>" + address + "</code> </H1><HR><code>"
                + stripedInfo + "<br>" + balanceLink
                + EXPLORER_FOOTER;
    }

    private String balance(String[] params) {
        if ((params.length >= 3) && (params[2].length() > 0)) {
            try {
                String[] scanobjects = new String[1];
                scanobjects[0] = "addr(" + params[2] + ")";
                String tmp = client.query("scantxoutset", "start", scanobjects);
                JSONObject json = new JSONObject(tmp);
                double total_amount = json.getDouble("total_amount");
                JSONArray unspents = json.getJSONArray("unspents");
                StringBuilder utxo = new StringBuilder("<TABLE>");
                for (int i = 0; i < unspents.length(); i++) {
                    JSONObject jsonUtxo = unspents.getJSONObject(i);
                    double value = jsonUtxo.getDouble("amount");
                    int height = jsonUtxo.getInt("height");
                    String txid = jsonUtxo.getString("txid");
                    int vout = jsonUtxo.getInt("vout");
                    utxo.append("<TR><TD>&nbsp</TD><TD>[")
                            .append(i)
                            .append("]</TD><TD>&nbsp</TD><TD>")
                            .append(DF8.format(value))
                            .append(" EAC</TD><TD>&nbsp</TD>")
                            .append("<TD>block: <A href=\"/explorer/")
                            .append(height)
                            .append("\">")
                            .append(height)
                            .append("</A></TD>")
                            .append("<TD>&nbsp</TD><TD>txid: <A href=\"/transaction/")
                            .append(txid)
                            .append("\">")
                            .append(txid)
                            .append("</A></TD>")
                            .append("<TD>&nbsp</TD><TD>tx_output_index: ")
                            .append(vout)
                            .append("</TD></TR>");
                }
                utxo.append("</TABLE>");
                return EXPLORER_HEADER
                        + "<H3>Unspent balance at address <code>" + params[2] + "</code> </H1><HR><code>"
                        + "<B>Total amount:</b> " + DF8.format(total_amount) + " EAC<br><br>"
                        + "Count of unspent transactions: " + unspents.length()
                        + utxo
                        + EXPLORER_FOOTER;
            } catch (JSONException ex) {
                return "request err";
            }
        }
        return "request err";
    }

    private String search(String[] params) {
        if ((params.length >= 3) && (params[2].length() > 0)) {
            params[2] = params[2].substring(3).replace("+", "").trim();  // remove "?q="
            System.out.println(params[2]);
            try {
                String testParam = client.query("validateaddress", params[2]);
                JSONObject json = new JSONObject(testParam);
                try {
                    boolean isvalid = json.getBoolean("isvalid");
                    if (isvalid) {
                        return addressinfo(testParam, params[2]);
                    } else {
                        throw new JSONException("Invalid address");
                    }
                } catch (JSONException ex) {
                    String[] tmpParams = {"", "extx", params[2]};
                    testParam = getrawtransaction(tmpParams);
                    try {
                        JSONObject json2 = new JSONObject(testParam);
                        String txid = json2.getString("txid");
                        return transaction(testParam);
                    } catch (JSONException ex2) {
                        return explore(params);
                    }
                }
            } catch (JSONException ex) {
                return "request err";
            }
        }
        return "request err";
    }

    private boolean addressValidationInternal(String address) {
        String tmp = client.query("validateaddress", address);
        JSONObject json = new JSONObject(tmp);
        return json.optBoolean("isvalid");
    }

    private JSONObject getUnspentBalanceInternal(String address) {
        String[] scanobjects = new String[1];
        scanobjects[0] = "addr(" + address + ")";
        String tmp = client.query("scantxoutset", "start", scanobjects);
        return new JSONObject(tmp);
    }

    private JSONArray selectSourcesInternal(JSONArray arrayUnspents, double amountToSent) {
        JSONArray rts = new JSONArray();
        double selected = 0.0;
        for (int i = 0; i < arrayUnspents.length(); i++) {
            JSONObject jsonTxo = arrayUnspents.getJSONObject(i);
            selected += jsonTxo.getDouble("amount");
            rts.put(jsonTxo);
            if (selected > amountToSent) {
                break;
            }
        }
        return rts;
    }

    private String doubleToFixedDecimalInternal(double value) {
        return String.format("%.8f", value).replace(",", ".");
    }

    private String createTransactionInternal(JSONArray selectedSources, double amountToSent, String targetAddress, String sourceAddress) {
        JSONArray inputs = new JSONArray();
        JSONArray outputs = new JSONArray();
        double selected = 0.0;
        double fee = 0.001;

        for (int i = 0; i < selectedSources.length(); i++) {
            JSONObject jsonTxo = selectedSources.getJSONObject(i);
            selected += jsonTxo.getDouble("amount");
            JSONObject input = new JSONObject();
            input.put("txid", jsonTxo.getString("txid"));
            input.put("vout", jsonTxo.getInt("vout"));
            inputs.put(input);
        }
        JSONObject output1 = new JSONObject("{\"" + targetAddress + "\":" + doubleToFixedDecimalInternal(amountToSent) + "}");
        outputs.put(output1);
        double sendBackAmount = selected - amountToSent;
        if (sendBackAmount > fee) {
            sendBackAmount -= fee;
            JSONObject output2 = new JSONObject("{\"" + sourceAddress + "\":" + doubleToFixedDecimalInternal(sendBackAmount) + "}");
            outputs.put(output2);
        }
        String rts = client.query("createrawtransaction", inputs, outputs);
        return rts;
    }

    private String signTransactionInternal(String rawTx, String privKey) throws Exception {
        JSONArray keys = new JSONArray();
        keys.put(privKey);
        String tmp = client.query("signrawtransactionwithkey", rawTx, keys);
        JSONObject json = new JSONObject(tmp);
        boolean complete = json.optBoolean("complete", false);
        if (!complete) {
            throw (new Exception("Transaction signing failed. Probably a bad private key."));
        }
        return json.getString("hex");
    }

    private String sendTransactionInternal(String signTx) {
        String rts = client.query("sendrawtransaction", signTx);
        try {
            JSONObject jsonRts = new JSONObject(rts);
        } catch (JSONException ex) {
            return "{\"txid\": \"" + rts + "\"}\n";
        }
        return rts;
    }


    private String sendCoins(String[] params) {
        try {
            if ((params.length != 6) || (params[2].length() == 0) || (params[3].length() == 0) || (params[4].length() == 0) || (params[5].length() == 0)) {
                throw (new Exception("This API method requires exactly 4 parameters - an address to receive coins, the amount to be send, the source address and the base58-encoded private key for signing the transaction."));
            }
            double amountToSent;
            try {
                amountToSent = Double.parseDouble(params[3]);
            } catch (NumberFormatException ex) {
                throw (new Exception("The 2nd parameter should be a number value."));
            }
            try {
                Base58.decodeChecked(params[5]);
            } catch (IllegalArgumentException ex) {
                throw (new Exception("The 4th parameter should be a valid base58-encoded key."));
            }
            if (!(addressValidationInternal(params[2]))) {
                throw (new Exception("The 1st parameter should be a valid address to receive transaction."));
            }
            if (!(addressValidationInternal(params[4]))) {
                throw (new Exception("The 3th parameter should be a valid address with a balance as the transaction source."));
            }
            JSONObject jsonUnspent = getUnspentBalanceInternal(params[4]);
            double balance = jsonUnspent.optDouble("total_amount", 0);
            if (amountToSent > balance) {
                throw (new Exception("Unsufficient balance on source address " + params[4]));
            }
            JSONArray arrayUnspents = jsonUnspent.getJSONArray("unspents");
            JSONArray selectedSources = (arrayUnspents.length() > 1) ? selectSourcesInternal(arrayUnspents, amountToSent) : arrayUnspents;
            String rawTx = createTransactionInternal(selectedSources, amountToSent, params[2], params[4]);
            String signTx = signTransactionInternal(rawTx, params[5]);
            return sendTransactionInternal(signTx);
        } catch (Exception ex) {
            return "{\"Error\": \"" + ex.getMessage() + "\"}\n";
        }
    }
}
