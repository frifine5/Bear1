/*
 * SoftAlgJni.java description
 * begin  : 2017.06.30
 * author : jgq
 */
package com.hoofoo.android.client;

import android.util.Log;

import com.cy.common.bus.L;

public class SoftAlgJni {
	public final static int SM4_ENCRYPT = 1; // mode :sm4 encrypt
	public final static int SM4_DECRYPT = 0; // mode :sm4 decrypt

	public static SoftAlgJni instance = null;
	public static boolean isLoad = false;

	private SoftAlgJni() {
	}

	public static SoftAlgJni getInstance(){
		if(!isLoad){
			try{
				System.loadLibrary("softalgjni");
				isLoad = true;
			}catch (Throwable t){
				Log.e(L.BUS, "算法库加载失败", t);
			}
		}
		if(null == instance){
			instance = new SoftAlgJni();
		}
		return instance;
	}




	/*
         * native methods that are implemented by the 'softalgjni' native library,
         * which is packaged with this application.
         */
	// sm3
	// 1.sm3
	/*
	 * function name: hf_a_sm3
	 * description:   sm3 hash
	 * params:
	 * @byte[] [in]      input          待摘要的数据
	 * @int    [in]      ilen           长度
	 * @byte[] [out]     output         hash data 
	 * @int    [in/out]  pioutlen       长度(32 bytes) 
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm3(byte[] input, int ilen, byte[] output,
			int[] pioutlen);

	// 2.sm3 hash init with id
	/*
	 * function name: hf_a_sm3_starts_withID
	 * description:   sm3 hash init with id
	 * params:
	 * @byte[] [out]      ctx          context
	 * @int    [in/out]   pictxlen     长度 (232 bytes)(sizeof(struct sm3 context))
	 * @byte[] [in]       ID           user id
	 * @int    [in]       IDLength     长度 
	 * @byte[] [in]       pk           ecc pubkey
	 * @int    [in]       pkLength     长度 (132 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm3_starts_withID(byte[] ctx, int[] pictxlen,
			byte[] ID, int IDLength, byte[] pk, int pkLength);

	// 3.sm3 hash update
	/*
	 * function name: hf_a_sm3_update
	 * description:   sm3 hash update
	 * params:
	 * @byte[] [out]      ctx          context
	 * @int    [in/out]   pictxlen     长度 (232 bytes)(sizeof(struct sm3 context))
	 * @byte[] [in]       input        待摘要的数据
	 * @int    [in]       ilen         长度 
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm3_update(byte[] ctx, int[] pictxlen, byte[] input,
			int ilen);

	// 4.sm3 hash final
	/*
	 * function name: hf_a_sm3_finish
	 * description:   sm3 hash finish
	 * params:
	 * @byte[] [out]      ctx          context
	 * @int    [in/out]   pictxlen     长度 (232 bytes)(sizeof(struct sm3 context))
	 * @byte[] [in]       output       sm3摘要数据
	 * @int    [in]       pioutlen     长度 (32 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm3_finish(byte[] ctx, int[] pictxlen,
			byte[] output, int[] pioutlen);

	// sm4
	// 5.sm4 encrypt init (key,16 bytes)
	/*
	 * function name: hf_a_sm4_setkey_enc
	 * description:   sm4 encrypt init
	 * params:
	 * @byte[] [out]      ctx          context
	 * @int    [in/out]   pictxlen     长度 (132 bytes)(sizeof(struct sm4 context))
	 * @byte[] [out]      key          对称密钥
	 * @int    [in]       ikeylen      长度 (16 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm4_setkey_enc(byte[] ctx, int[] pictxlen,
			byte[] key, int ikeylen);

	// 6.sm4 decrypt init (key,16 bytes)
	/*
	 * function name: hf_a_sm4_setkey_dec
	 * description:   sm4 decrypt init
	 * params:
	 * @byte[] [out]      ctx          context
	 * @int    [in/out]   pictxlen     长度 (132 bytes)(sizeof(struct sm4 context))
	 * @byte[] [out]      key          对称密钥
	 * @int    [in]       ikeylen      长度 (16 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm4_setkey_dec(byte[] ctx, int[] pictxlen,
			byte[] key, int ikeylen);

	// 7.sm4 ecb encrypt or decrypt
	/*
	 * function name: hf_a_sm4_crypt_ecb
	 * description:   sm4 encrypt or decrypt ecb
	 * params:
	 * @byte[] [in]      ctx         context
	 * @int    [in]      ictxlen     长度 (132 bytes)(sizeof(struct sm4 context))
	 * @int    [in]      mode        SM4_ENCRYPT or SM4_DECRYPT
	 * @byte[] [in]      input       明文
	 * @int    [in]      length      长度 
	 * @byte[] [out]     output      密文
	 * @int    [in/out]  pioutputlen 长度 (=明文长度)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm4_crypt_ecb(byte[] ctx, int ictxlen, int mode,
			byte[] input, int length, byte[] output, int[] pioutputlen);

	//8.sm4 cbc encrypt or decrypt
	/*
	 * function name: hf_a_sm4_crypt_cbc
	 * description:   sm4 encrypt or decrypt cbc
	 * params:
	 * @byte[] [in]      ctx         context
	 * @int    [in]      ictxlen     长度 (132 bytes)(sizeof(struct sm4 context))
	 * @int    [in]      mode        SM4_ENCRYPT or SM4_DECRYPT
	 * @byte[] [in]      iv          iv向量(updated after use)
	 * @int    [in]      ivlen       长度(16 bytes) 
	 * @byte[] [in]      input       明文
	 * @int    [in]      length      长度 
	 * @byte[] [out]     output      密文
	 * @int    [in/out]  pioutputlen 长度 (=明文长度)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_sm4_crypt_cbc(byte[] ctx, int ictxlen, int mode,
			byte[] iv,int ivlen,
			byte[] input, int length, byte[] output, int[] pioutputlen);
	
	// sm2
	//sm2 sys init,sm2 sys clear
	/*
	 * bw_sm2_sys_init初始化曲线参数, 分配存储空间； 
	 * bw_sm2_sys_clear清除；
	 * 必须在调用sm2各函数之前调用bw_sm2_sys_init,
	 * 程序启动时调用init, 退出时clear。
	 */
	// 9.sm2 sys init
	/*
	 * function name: hf_a_bw_sm2_sys_init
	 * description:   sm2 sys init
	 * params:
	 *   
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_sys_init();

	// 10.sm2 sys clear
	/*
	 * function name: hf_a_bw_sm2_sys_clear
	 * description:   sm2 sys clear
	 * params:
	 *   
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_sys_clear();

	//客户端密钥生成
	// 11.hf_a_bw_sm2_tps_client_keygen
	/*
	 * function name: hf_a_bw_sm2_tps_client_keygen
	 * description:   客户端输出客户端私钥和向服务端的发送数据。
	 * params:
	 * @byte[] [in]      rnd              random
	 * @int    [in]      rndlen           长度(32 bytes)
	 * @byte[] [out]     sk               客户端私钥
	 * @int    [in/out]  pisklen          length(64 bytes)
	 * @byte[] [out]     out_exdata       要向服务端发送的数据    
	 * @int    [in/out]  piout_exdatalen  长度(65 bytes) 
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tps_client_keygen(byte[] rnd, int rndlen, // the
																			// random
			byte[] sk, int[] pisklen, // the client prikey
			byte[] out_exdata, int[] piout_exdatalen // send to server: = d1*g
	);

	// 12.client pubkey verify  
	/*
	 * function name: hf_a_bw_sm2_tps_client_pubkey_verify
	 * description:   sm2验证公钥(客户端对服务端发送来的公钥pk和自己根据in_exdata计算出来的公钥比较。
	 *                           若两者一致，则秘钥生成成功。)
	 * params:
	 * @byte[] [in]      pk              sm2公钥
	 * @int    [in]      pklen           长度(65 bytes)
	 * @byte[] [in]      in_exdata       服务端发送来的中间数据
	 * @int    [in]      in_exdatalen    length(65 bytes)
	 * @byte[] [in]      sk              客户端私钥
	 * @int    [in]      sklen           长度(64 bytes) 
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tps_client_pubkey_verify(byte[] pk,
			int pklen, // the pubkey that the server outputs
			byte[] in_exdata, int in_exdatalen, byte[] sk, int sklen);

	// 13.sm2 client init  hf_a_bw_sm2_tps_client_init
	/*
	 * function name: hf_a_bw_sm2_tps_client_init
	 * description:   sm2双方签名--客户端初始化context
	 * params:
	 * @byte[] [out]      ctx             context上下文
	 * @int    [in/out]   pictxlen        长度(1024 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tps_client_init(byte[] ctx, int[] pictxlen);

	// 14.hf_a_bw_sm2_tps_client_hello
	/*
	 * function name: hf_a_bw_sm2_tps_client_hello
	 * description:   sm2双方签名--客户端向服务端发送数据
	 * params:
	 * @byte[] [in]      ctx             context上下文
	 * @int[]  [in]      ictxlen         长度(1024 bytes)
	 * @byte[] [in]      em              hash data
	 * @int    [in]      iemlen          长度(32 bytes)
	 * @byte[] [in]      rnd1            random
	 * @int    [in]      irnd1len        长度(32 bytes)
	 * @byte[] [out]     out_exdata      发送给服务端的数据
	 * @int    [in/out]  piout_exdatalen 长度(97 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tps_client_hello(byte[] ctx, int ictxlen,
			byte[] em, int iemlen, // hv(Za || M)
			byte[] rnd1, int irnd1len, 
			byte[] out_exdata, int[] piout_exdatalen // em ||  rnd1*g
	);

	// 15.hf_a_bw_sm2_tps_client_final
	/*
	 * function name: hf_a_bw_sm2_tps_client_final
	 * description:   sm2双方签名--客户端输出签名值
	 * params:
	 * @byte[] [in]      ctx             context上下文
	 * @int    [in]      ictxlen         长度(1024 bytes)
	 * @byte[] [in]      in_exdata       服务端发送过来的数据
	 * @int    [in]      iin_exdatalen   长度(96 bytes)
	 * @byte[] [in]      sk              客户端私钥
	 * @int    [in]      isklen          长度(64 bytes)
	 * @byte[] [out]     sig             signature
	 * @int    [in/out]  isiglen         length(64 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tps_client_final(byte[] ctx, int ictxlen,
			byte[] in_exdata, int iin_exdatalen, 
			byte[] sk, int isklen, // client prikey
			byte[] sig, int[] pisiglen // outputs the signature
	);

	// 16.hf_a_bw_sm2_tps_client_clear
	/*
	 * function name: hf_a_bw_sm2_tps_client_clear
	 * description:   sm2双方签名客户端清理context
	 * params:
	 * @byte[] [in]      ctx       context 上下文
	 * @int    [in]      ictxlen   长度(1024 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tps_client_clear(byte[] ctx, int ictxlen);

	// 17.hf_a_bw_sm2_verify
	/*
	 * function name: hf_a_bw_sm2_verify
	 * description:   sm2验签
	 * params:
	 * @byte[] [in]      sig             signature
	 * @int    [in]      isiglen         length(64 bytes)
	 * @byte[] [in]      em              hash data
	 * @int    [in]      iemlen          长度(32 bytes)
	 * @byte[] [in]      pk              sm2公钥
	 * @int    [in]      ipklen          长度(65 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_verify(byte[] sig, int isiglen, 
			byte[] em,int iemlen, // hv(Za || M)
			byte[] pk, int ipklen // pubkey
	);

	// 18.sm2 encrypt  hf_a_bw_sm2_enc
	/*
	 * function name: hf_a_bw_sm2_enc
	 * description:   sm2加密
	 * params:
	 * @byte[] [in]      msg             明文
	 * @int    [in]      mlen            明文长度
	 * @byte[] [in]      pk              sm2公钥
	 * @int    [in]      ipklen          长度(65 bytes)
	 * @byte[] [in]      rnd             random
	 * @int    [in]      irndlen         长度(32 bytes)
	 * @byte[] [out]     cypher          密文
	 * @int    [in/out]  piclen          密文长度(明文长度+100字节)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_enc(byte[] msg, int mlen, byte[] pk,
			int ipklen, byte[] rnd, int irndlen, byte[] cypher, int[] piclen);
	// 19.sm2 decrypt  hf_a_bw_sm2_dec
	/*
	 * function name: hf_a_bw_sm2_dec
	 * description:   sm2解密
	 * params:
	 * @byte[] [in]      cypher          密文
	 * @int    [in]      clen            密文长度
	 * @byte[] [in]      sk              sm2私钥
	 * @int    [in]      isklen          长度(32 bytes)
	 * @byte[] [out]     msgs            明文
	 * @int    [in/out]  pimlen          明文长度(密文长度-100字节)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_dec(byte[] cypher,int clen,
			byte[] sk,int isklen,byte[] msgs,int[] pimlen);
	// 20.hf_a_bw_sm2_tpe_client_hello
	/*
	 * function name: hf_a_bw_sm2_tpe_client_hello
	 * description:   双方解密 --客户端从cypher中提取C1到out_exdata. 
	 * params:
	 * @byte[] [in]      cypher          密文
	 * @int    [in]      clen            密文长度
	 * @byte[] [out]     out_exdata      密文的前65字节(C1)
	 * @int    [in/out]  piout_exdatalen 长度(65字节)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tpe_client_hello(byte[] cypher, int clen,
			byte[] out_exdata, int[] piout_exdatalen // first 65 bytes of c1-cypher
	);

	// 21.hf_a_bw_sm2_tpe_client_final
	/*
	 * function name: hf_a_bw_sm2_tpe_client_final
	 * description:   双方解密 --客户端输出明文
	 * params:
	 * @byte[] [in]      in_exdata       
	 * @int    [in]      iin_exdatalen   长度
	 * @byte[] [in]      cypher          密文
	 * @int    [in]      clen            密文长度
	 * @byte[] [in]      sk              客户端私钥
	 * @int    [in]      isklen          长度(64 bytes)
	 * @byte[] [out]     msg             明文
	 * @int    [in/out]  pimlen          明文长度(密文长度-100字节)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tpe_client_final(byte[] in_exdata,
			int iin_exdatalen, byte[] cypher, int clen, 
			byte[] sk, int isklen, // client prikey
			byte[] msg, int[] pimlen // plain
	);

	// 22.hf_a_bw_sm2_tpkx_nocomfirm_init
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_init
	 * description:   密钥协商初始化context
	 * params:
	 * @int    [in]      is_sender           是否是发起方(1:发起方,0:响应方) 
	 * @byte[] [out]     sm2_tpkx_ctx        context 上下文
	 * @int    [in/out]  pism2_tpkx_ctxlen   长度(1024 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 * 修改历史:
	 * 1.2017.08.09 jgq del
	 */
	//public native int hf_a_bw_sm2_tpkx_nocomfirm_init(
	//		int is_sender, // is Sponsor
	//		byte[] sm2_tpkx_ctx, int[] pism2_tpkx_ctxlen);

	// 23.hf_a_bw_sm2_tpkx_nocomfirm_clear
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_clear
	 * description:   密钥协商清除context
	 * params:
	 * @byte[] [in]      sm2_tpkx_ctx       context 上下文
	 * @int    [in]      ism2_tpkx_ctxlen   长度(1024 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 * 修改历史:
	 * 1.2017.08.09 jgq del
	 */
	//public native int hf_a_bw_sm2_tpkx_nocomfirm_clear(byte[] sm2_tpkx_ctx,
	//		int ism2_tpkx_ctxlen);

	// 24.hf_a_bw_sm2_tpkx_nocomfirm_sender_hello
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_sender_hello
	 * description:   密钥协商发起方计算发起方临时公钥
	 * params:
	 * @byte[] [out]     sm2_tpkx_ctx       context 上下文
	 * @int[]  [in/out]  pism2_tpkx_ctxlen  长度(1024 bytes)
	 * @byte[] [in]      rnd                random
	 * @int    [in]      irndlen            长度(32 bytes)
	 * @byte[] [out]     out_exdata         发送给响应方的数据(发起方的公钥)
	 * @int    [in/out]  piout_exdatalen    长度(65 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 修改  ctx由入参修改成出参
	 */
	public native int hf_a_bw_sm2_tpkx_nocomfirm_sender_hello(
			byte[] sm2_tpkx_ctx, int[] pism2_tpkx_ctxlen, byte[] rnd, int irndlen,
			byte[] out_exdata, int[] piout_exdatalen // data send to B
	);

	// 25.hf_a_bw_sm2_tpkx_nocomfirm_sender_query_server
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_sender_query_server
	 * description:   密钥协商发起方计算发送给服务端的数据
	 * params:
	 * @byte[] [in]      in_exdata          响应方的临时公钥
	 * @int    [in]      iin_exdatalen      长度(65 bytes)
	 * @byte[] [in]      sm2_tpkx_ctx       context 上下文
	 * @int    [in]      ism2_tpkx_ctxlen   长度(1024 bytes)
	 * @byte[] [in]      self_pubkey        发起方公钥
	 * @int    [in]      iself_pubkeylen    长度(65 bytes)
	 * @byte[] [in]      other_pubkey       响应方公钥
	 * @int    [in]      iother_pubkeylen   长度(65 bytes)
	 * @byte[] [in]      self_z             发起方za
	 * @int    [in]      iself_zlen         长度(32 bytes)
	 * @byte[] [in]      other_z            响应方za
	 * @int    [in]      iother_zlen        长度(32 bytes)
	 * @byte[] [out]     out_exdata         发送给服务端的数据
	 * @int    [in/out]  piout_exdatalen    长度(65 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tpkx_nocomfirm_sender_query_server(
			byte[] in_exdata,
			int iin_exdatalen, // data B send
			byte[] sm2_tpkx_ctx, int ism2_tpkx_ctxlen, byte[] self_pubkey,
			int iself_pubkeylen, // self pubkey
			byte[] other_pubkey, int iother_pubkeylen, // other pubkey
			byte[] self_z, int iself_zlen, // self Za
			byte[] other_z, int iother_zlen, // other Zb
			byte[] out_exdata, int[] piout_exdatalen // data send to A's server
	);

	// 26.hf_a_bw_sm2_tpkx_nocomfirm_sender_final
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_sender_final
	 * description:   密钥协商发起方输出密钥
	 * params:
	 * @byte[] [in]      in_exdata          发起方接收到的服务端发送来的数据
	 * @int    [in]      iin_exdatalen      长度
	 * @int    [in]      klen               要协商的密钥长度(一般16个字节)
	 * @byte[] [in]      sm2_tpkx_ctx       context 上下文
	 * @int    [in]      ism2_tpkx_ctxlen   长度(1024 bytes)
	 * @byte[] [in]      sk                 发起方私钥
	 * @int    [in]      isklen             长度(64 bytes)
	 * @byte[] [out]     key                发起方输出的协商密钥
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tpkx_nocomfirm_sender_final(byte[] in_exdata,
			int iin_exdatalen, // data, A's server send
			int klen, // key length(bytes)
			byte[] sm2_tpkx_ctx, int ism2_tpkx_ctxlen, 
			byte[] sk, int isklen, // self part prikey
			byte[] key // outputs the agreement key
	);

	// 27.
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_responsor_s1
	 * description:   密钥协商响应方计算发送给发起方和服务端的数据
	 * params:
	 * @byte[] [in]      in_exdata          发送方的临时公钥
	 * @int    [in]      iin_exdatalen      长度
	 * @byte[] [out]     sm2_tpkx_ctx       context 上下文
	 * @int[]  [in/out]  pism2_tpkx_ctxlen  长度(1024 bytes)
	 * @byte[] [in]      rnd                random 
	 * @int    [in]      irndlen            长度(32 bytes)
	 * @byte[] [in]      self_pubkey        响应方公钥
	 * @int    [in]      iself_pubkeylen    长度(65 bytes)
	 * @byte[] [in]      other_pubkey       发送方公钥
	 * @int    [in]      iother_pubkeylen   长度(65 bytes)
	 * @byte[] [in]      self_z             响应方za
	 * @int    [in]      iself_zlen         长度(32 bytes)
	 * @byte[] [in]      other_z            发起方za
	 * @int    [in]      iother_zlen        长度(32 bytes)
	 * @byte[] [out]     out_exdata_to_sender         发送给发起方的数据(响应方的临时公钥)
	 * @int    [in/out]  piout_exdata_to_senderlen    长度(65 bytes)
	 * @byte[] [out]     out_exdata_to_server         发送给服务端的数据
	 * @int    [in/out]  piout_exdata_to_serverlen    长度(65 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 修改  ctx由入参修改成出参
	 */
	public native int hf_a_bw_sm2_tpkx_nocomfirm_responsor_s1(
			byte[] in_exdata,
			int iin_exdatalen, // data receive from A
			byte[] sm2_tpkx_ctx, int[] pism2_tpkx_ctxlen, byte[] rnd, int irndlen,
			byte[] self_pubkey, int iself_pubkeylen, // self pubkey
			byte[] other_pubkey, int iother_pubkeylen, // other pubkey
			byte[] self_z, int iself_zlen, // self Za
			byte[] other_z, int iother_zlen, // other Zb
			byte[] out_exdata_to_sender,// data need to send to SponsorA
			int[] piout_exdata_to_senderlen, 
			byte[] out_exdata_to_server, 
			int[] piout_exdata_to_serverlen // data need to send to self server
	);

	// 28.hf_a_bw_sm2_tpkx_nocomfirm_responsor_final
	/*
	 * function name: hf_a_bw_sm2_tpkx_nocomfirm_responsor_final
	 * description:   密钥协商响应方输出密钥
	 * params:
	 * @byte[] [in]      in_exdata          响应方接收到的服务端发送来的数据
	 * @int    [in]      iin_exdatalen      长度
	 * @int    [in]      klen               要协商的密钥长度(一般16个字节)
	 * @byte[] [in]      sm2_tpkx_ctx       context 上下文
	 * @int    [in]      ism2_tpkx_ctxlen   长度
	 * @byte[] [in]      sk                 响应方私钥
	 * @int    [in]      isklen             长度(64 bytes)
	 * @byte[] [out]     key                响应方输出的协商密钥
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int hf_a_bw_sm2_tpkx_nocomfirm_responsor_final(
			byte[] in_exdata, int iin_exdatalen, // data receive from server
			int klen, // key length(bytes)
			byte[] sm2_tpkx_ctx, int ism2_tpkx_ctxlen,
			byte[] sk, int isklen, // self part prikey
			byte[] key // outputs the agreement key
	);
	
	//2017.07.20 jgq add  对称加解密时补码和去除补码
	//29.补码   padding
	/*
	 * function name: hf_a_M_Convert_FinalData
	 * description:   对称加密时，如果明文长度不是16的倍数需要补码
	 * params:
	 * @int    [in]      MK_Module_Len      value = 16
	 * @int    [in/out]  MK_Data_Len        [in]要补码的数据[out]补码后的数据
	 * @byte[] [in/out]  MK_Data            [in]要补码的数据长度[out]补码后的数据长度
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native void hf_a_M_Convert_FinalData(int MK_Module_Len,int[] MK_Data_Len,byte[] MK_Data);

	//30.去除补码  unpadding
	/*
	 * function name: hf_a_M_Resume_FinalData
	 * description:   对称加密时，如果明文长度不是16的倍数需要补码,解密结束后，需要去补码，
	 *                如果加密时未做补码，解密后去补码会出错。
	 * params:
	 * @int    [in/out]  MK_Data_Len        [in]需要去补码的数据[out]去补码后的数据
	 * @byte[] [in/out]  MK_Data            [in]需要去补码的数据长度[out]去补码后的数据长度
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_M_Resume_FinalData(int[] MK_Data_Len,byte[] MK_Data);
	
	//31.hf_a_bw_sm2_za
	/*
	 * function name: hf_a_bw_sm2_za
	 * description:   计算za (za = sm3(entl || id || sys_params || pk_x || pk_y), pk是用户公钥 )
	 * params:
	 * @byte[] [in]      id          user id 
	 * @int    [in]      id_len      length
	 * @byte[] [in]      pk          user pubkey
	 * @int    [in]      pklen       length(65 bytes)
	 * @byte[] [out]     za          
	 * @int[]  [in/out]  zalen       length(32 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_sm2_za(byte[] id,int id_len,
			byte[] pk,int pklen,byte[] za,int[] zalen);
	
	//sm9
	//sm9 sys init,sys clear
	/*
	 * bw_ibe_sm9_sys_init按照sm9标准第五部分初始化曲线参数, 分配存储空间； 
	 * bw_ibe_sm9_sys_clear清除；
	 * 必须在调用sm9各函数之前调用bw_ibe_sm9_sys_init,
	 * 程序启动时调用init, 退出时clear。
	 */
	//32.hf_a_bw_ibe_sm9_sys_init
	/*
	 * function name: hf_a_bw_ibe_sm9_sys_init
	 * description:   sm9 sys init
	 * params:
	 *   
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_ibe_sm9_sys_init();
	
	//33.hf_a_bw_ibe_sm9_sys_clear
	/*
	 * function name: hf_a_bw_ibe_sm9_sys_clear
	 * description:   sm9 sys clear
	 * params:
	 *   
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_ibe_sm9_sys_clear();
	
	//sm9 sign
	//34.hf_a_bw_ibe_sm9_tps_sign_user_init_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tps_sign_user_init_m
	 * description:   双方签名--客户端初始化
	 * params:
	 * @byte[] [in]      sk          part private key user saved(65 bytes)      
	 * @int    [in]      isklen      length
	 * @byte[] [in]      ppubs       kgc's master key
	 * @int    [in]      ippubslen   length
	 * @byte[] [out]     ctx         
	 * @int[]  [in/out]  pictxlen    1024 bytes
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_ibe_sm9_tps_sign_user_init_m(byte[] sk,int isklen,
			byte[] ppubs,int ippubslen,byte[] ctx,int[] pictxlen);
		    
	//35.hf_a_bw_ibe_sm9_tps_sign_user_s1_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tps_sign_user_s1_m
	 * description:   双方签名--客户端接收服务端发送的数据，计算并发送结果给服务端，
	 *                这是一次完成msg输入的。
	 * params:
	 * @byte[] [in]      in_exdata          received data from server      
	 * @int    [in]      iin_exdatalen      length
	 * @byte[] [in]      msg                sign message
	 * @int    [in]      msg_length         length
	 * @byte[] [in]      rnd1               random
	 * @int    [in]      rnd1len            length(32 bytes)
	 * @byte[] [in/out]  ctx                1024 bytes
	 * @int[]  [in/out]  pictxlen    
	 * @byte[] [out]     out_exdata         data need to send to server
	 * @int[]  [in/out]  piout_exdatalen    length
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_ibe_sm9_tps_sign_user_s1_m(byte[] in_exdata,int iin_exdatalen,
			byte[] msg,int msg_length,byte[] rnd1,int rnd1len,
			byte[] ctx,int[] pictxlen,byte[] out_exdata,int[] piout_exdatalen);
	
	//36.hf_a_bw_ibe_sm9_tps_sign_user_s2_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tps_sign_user_s2_m
	 * description:   双方签名--客户端接收服务端发送的数据，计算并输出签名值。
	 * params:
	 * @byte[] [in]      ctx              
	 * @int    [in]      ictxlen      
	 * @byte[] [in]      exdata      data received from server(k2)         
	 * @int    [in]      iexdatalen          
	 * @byte[] [out]     sig         signature
	 * @int[]  [in/out]  pisiglen    length(128 bytes)
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_ibe_sm9_tps_sign_user_s2_m(byte[] ctx,int ictxlen,
				byte[] exdata,int iexdatalen,byte[] sig,int[] pisiglen);
		
	//37.hf_a_bw_ibe_sm9_tps_sign_user_clear_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tps_sign_user_clear_m 
	 * description: 双方签名--客户端清除context 
	 * params:
	 * @byte[] [in] ctx 
	 * @int    [in] ictxlen 
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int  hf_a_bw_ibe_sm9_tps_sign_user_clear_m(byte[] ctx,int ictxlen);
	
	//sm9 verify
	//38.hf_a_bw_ibe_sm9_verify
	/*
	 * function name: hf_a_bw_ibe_sm9_verify 
	 * description: sm9验签
	 * params:
	 * @byte[] [in] sig         签名值
	 * @int    [in] isiglen 
	 * @byte[] [in] pubs        签名主公钥
	 * @int    [in] ipubslen
	 * @byte[] [in] ida         用户公钥标识
	 * @int    [in] idalen
	 * @byte[] [in] msg         被签名数据
	 * @int    [in] msg_length
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int  hf_a_bw_ibe_sm9_verify(byte[] sig,int isiglen,
			byte[] pubs,int ipubslen,byte[] ida,int idalen,
			byte[] msg,int msg_length);
	
	//sm9 encrypt
	//39.hf_a_bw_ibe_sm9_enc_sm4
	/*
	 * function name: hf_a_bw_ibe_sm9_enc_sm4 
	 * description: sm4方式加密. ecb模式，输入msg需要经过填充。
	 * params:
	 * @byte[] [in] msg         已填充好的明文。长度应为16的倍数。
	 * @int    [in] mlen        明文长度
	 * @byte[] [in] uid         用户公钥标识
	 * @int    [in] iuidlen
	 * @byte[] [in] pube        加密主公钥
	 * @int    [in] ipubelen
	 * @byte[] [in] rnd         随机数
	 * @int    [in] irndlen     随机数长度(32 bytes)
	 * @byte[] [out] cypher     密文
	 * @int    [in/out] piclen  密文长度(mlen + 96)
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int  hf_a_bw_ibe_sm9_enc_sm4(byte[] msg,int mlen,byte[] uid,int iuidlen,
			byte[] pube,int ipubelen,byte[] rnd,int irndlen,byte[] cypher,int[] piclen);
	
	//sm9 decrypt
	//40.hf_a_bw_ibe_sm9_tpe_user_hello_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tpe_user_hello_m
	 * description:   双方解密--客户端向服务端请求，并发送交换数据
	 * params:     
	 * @byte[] [in]      cypher      cipher text         
	 * @int    [in]      clen        length
	 * @byte[] [out]     out_exdata         data send to server
	 * @int[]  [in/out]  piout_exdatalen    length
	 * return: 0         succeed,
	 *         else      failed,
	 */
	public native int  hf_a_bw_ibe_sm9_tpe_user_hello_m(byte[] cypher,int clen,
				byte[] out_exdata,int[] piout_exdatalen);
	
	//41.hf_a_bw_ibe_sm9_tpe_user_final_sm4_m
	/*
	* function name: hf_a_bw_ibe_sm9_tpe_user_final_sm4_m
	* description:   双方解密--客户端输出明文。加密方式为sm4
	* params:   
	* @byte[] [in]      in_exdata          收到的服务端的数据
	* @int    [in]      iin_exdatalen      length  
	* @byte[] [in]      cypher             cipher text         
	* @int    [in]      clen               length
	* @byte[] [in]      uid                用户公钥标识
	* @int    [in]      iuidlen            length
	* @byte[] [in]      de_user            用户部分私钥
	* @int    [in]      ide_userlen        length
	* @byte[] [out]     msg                message
	* @int[]  [in/out]  pimlen             length(clen - 96)
	* return: 0         succeed,
	*         else      failed,
	*/
	public native int  hf_a_bw_ibe_sm9_tpe_user_final_sm4_m(byte[] in_exdata,int iin_exdatalen,
				byte[] cypher,int clen,byte[] uid,int iuidlen,
				byte[] de_user,int ide_userlen,byte[] msg,int[] pimlen);
	
	//sm9 key exchange
	//42.hf_a_bw_ibe_sm9_tpkx_noconfirm_init
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_noconfirm_init 
	 * description:  initialize context params: 
	 * @byte[] [in] pube enc mast pubkey 
	 * @int    [in] ipubelen length 
	 * @byte[] [in] other_id  对方的公钥标识
	 *                       (如果是发起方初始化，则应为响应方id;如果是响应方初始化，则应为发起方id)
	 * @int    [in] iother_idlen length 
	 * @int    [in] is_sender (发起方: 1,响应方 : 0 )
	 * @byte[] [out] sm9_exch_ctx 
	 * @int[]  [in/out] pism9_exch_ctxlen length(1024 bytes)
	 * return: 0 succeed, 
	 *         else failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 删除
	 */
	//public native int  hf_a_bw_ibe_sm9_tpkx_noconfirm_init(byte[] pube,int ipubelen,
	//			byte[] other_id,int iother_idlen,int is_sender,
	//			byte[] sm9_exch_ctx,int[] pism9_exch_ctxlen);
	
	// 43.hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_hello_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_hello_m 
	 * description:  发起方发起秘钥协商，向响应方发送交换数据
	 * params:
	 * @byte[] [out]    sm9_exch_ctx
	 * @int[]  [in/out] pism9_exch_ctxlen  length(2048 bytes)
	 * @byte[] [in] pube 加密主公钥
	 * @int    [in] ipubelen 
	 * @byte[] [in] other_id  对方的公钥标识
	 * @int    [in] iother_idlen 
	 * @byte[] [in] rnd         random   
	 * @int    [in] irndlen     length(32 bytes)
	 * @byte[] [out] out_exdata
	 * @int[]  [in/out] piout_exdatalen length
	 * return: 0 succeed, 
	 *         else failed
	 * 修改历史：
	 * 1.2017.08.09 jgq 修改  增加加密主公钥和对方公钥标识2个参数，修改ctx为出参
	 */
	public native int hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_hello_m(
			byte[] sm9_exch_ctx, int[] pism9_exch_ctxlen, 
			byte[] pube,int ipubelen,
			byte[] other_id,int iother_idlen,byte[] rnd, int irndlen,
			byte[] out_exdata, int[] piout_exdatalen);

	// 44.hf_a_bw_ibe_sm9_tpkx_noconfirm_clear
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_noconfirm_clear 
	 * description: clear context
	 * params:
	 * @byte[] [in] sm9_exch_ctx 
	 * @int    [in] ism9_exch_ctxlen 
	 * return: 0 succeed, else failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 删除
	 */
	//public native int hf_a_bw_ibe_sm9_tpkx_noconfirm_clear(byte[] sm9_exch_ctx,
	//		int ism9_exch_ctxlen);
	
	//45.hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_query_server_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_query_server_m    
	 * description:  发起方接收响应方发回的交换数据，向服务端发送交换数据
	 * params:	 
	 * @byte[] [in] in_exdata            接收到的响应方发送的数据
	 * @int    [in] iin_exdatalen        length 
	 * @byte[] [in] sm9_exch_ctx
	 * @int    [in] ism9_exch_ctxlen     length
	 * @byte[] [out] out_exdata          要发送给服务端的数据
	 * @int[]  [in/out] piout_exdatalen  length 
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_query_server_m(
			byte[] in_exdata,int iin_exdatalen,
			byte[] sm9_exch_ctx,int ism9_exch_ctxlen,
			byte[] out_exdata, int[] piout_exdatalen);
	
	//46.hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_final_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_final_m    
	 * description:  发起方输出交换密钥
	 * params:	 
	 * @byte[] [in] in_exdata            收到的服务端发送来的数据
	 * @int    [in] iin_exdatalen        length 
	 * @byte[] [in] sm9_exch_ctx
	 * @int    [in] ism9_exch_ctxlen     length
	 * @int    [in] klen                 key length(16 bytes)
	 * @byte[] [in] de_user              发起方的部分私钥
	 * @int    [in] ide_userlen          
	 * @byte[] [in] self_id              发起方的公钥标识
	 * @int    [in] iself_idlen
	 * @byte[] [in] other_id             响应方的公钥标识
	 * @int    [in] iother_idlen            
	 * @byte[] [out] key                 协商密钥(agreement key)
	 * return: 0 succeed, 
	 *         else failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 修改  增加响应方的公钥标识
	 *         
	 */
	public native int hf_a_bw_ibe_sm9_tpkx_nocomfirm_sender_final_m(
			byte[] in_exdata,int iin_exdatalen,
			byte[] sm9_exch_ctx,int ism9_exch_ctxlen,
			int klen,
			byte[] de_user,int ide_userlen,
			byte[] self_id,int iself_idlen,
			byte[] other_id,int iother_idlen,
			byte[] key);
	
	//47.hf_a_bw_ibe_sm9_tpkx_nocomfirm_responsor_query_server_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_nocomfirm_responsor_query_server_m    
	 * description:  响应方接收发起方的交换数据in_exdata，向服务端发送交换数据out_exdata
	 * params:	 
	 * @byte[] [in] in_exdata            data received from sponsor
	 * @int    [in] iin_exdatalen        length 
	 * @byte[] [out] sm9_exch_ctx
	 * @int[]  [in/out] pism9_exch_ctxlen     length
	 * @byte[] [out] out_exdata          data need send to server
	 * @int[]  [in/out] piout_exdatalen  length 
	 * return: 0 succeed, 
	 *         else failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 修改  ctx由入参修改为出参
	 */
	public native int hf_a_bw_ibe_sm9_tpkx_nocomfirm_responsor_query_server_m(
			byte[] in_exdata,int iin_exdatalen,
			byte[] sm9_exch_ctx,int[] pism9_exch_ctxlen,
			byte[] out_exdata, int[] piout_exdatalen);
	
	//48.hf_a_bw_ibe_sm9_tpkx_nocomfirm_responsor_final_m
	/*
	 * function name: hf_a_bw_ibe_sm9_tpkx_nocomfirm_responsor_final_m    
	 * description:  响应方输出发给发起方的交换数据和得到的交换秘钥
	 * params:	 	  
	 * @byte[] [in] sm9_exch_ctx
	 * @int    [in] ism9_exch_ctxlen     length
	 * @byte[] [in] in_exdata            data received from server
	 * @int    [in] iin_exdatalen        length
	 * @int    [in] klen                 key length(16 bytes)
	 * @byte[] [in] de_user              响应方的部分私钥
	 * @int    [in] ide_userlen          length(32 bytes)
	 * @byte[] [in] pube                 加密主公钥
	 * @int    [in] ipubelen 
	 * @byte[] [in] self_id              响应方的公钥标识
	 * @int    [in] iself_idlen 
	 * @byte[] [in] other_id             发起方的公钥标识
	 * @int    [in] iother_idlen 
	 * @byte[] [in] rnd                  random
	 * @int    [in] irndlen              length(32 bytes)
	 * @byte[] [out] key                 agreement key
	 * @byte[] [out] out_exdata          要发送给发起方的数据
	 * @int[]  [in/out] piout_exdatalen  length  
	 * return: 0 succeed, 
	 *         else failed,
	 * 修改历史：
	 * 1.2017.08.09 jgq 修改   增加加密主公钥pube和发起方的公钥标识other_id
	 */
	public native int hf_a_bw_ibe_sm9_tpkx_nocomfirm_responsor_final_m(
			byte[] sm9_exch_ctx,int ism9_exch_ctxlen,
			byte[] in_exdata,int iin_exdatalen,
			int klen,
			byte[] de_user,int ide_userlen,
			byte[] pube,int ipubelen,
			byte[] self_id,int iself_idlen,
			byte[] other_id,int iother_idlen,
			byte[] rnd,int irndlen,
			byte[] key,
			byte[] out_exdata,int[] piout_exdatalen);
	//49.hf_a_as_sm2_key_split
	/*
	 * function name: hf_a_as_sm2_key_split   
	 * description: 将标准的单人sm2秘钥分解为服务端和客户端的秘钥
	 * params:	 	  
	 * @byte[] [in] sk         输入sm2私钥
	 * @int    [in] isklen     length(32 bytes)
	 * @byte[] [out] sk1                   输出客户端私钥
	 *                         当此参数为null时，由pisk1len返回长度
	 * @int[]  [in/out] pisk1len  length (64 bytes)
	 * @byte[] [out] sk2                   输出服务端私钥
	 *                         当此参数为null时，由pisk2len返回长度
	 * @int[]  [in/out] pisk2len  length (64 bytes)
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int hf_a_as_sm2_key_split(byte[] sk,int isklen,
			byte[] sk1,int[] pisk1len,byte[] sk2,int[] pisk2len);
	
	//50.hf_a_as_sm2_sign_rnd_split
	/*
	 * function name: hf_a_as_sm2_sign_rnd_split   
	 * description: 将标准的单人sm2签名过程中的输入随机数分解为联合签名的客户端和服务端的随机数
	 * params:	 	  
	 * @byte[] [in] rnd         输入随机数
	 * @int    [in] irndlen     length(32 bytes)
	 * @byte[] [out] rnd1                  输出随机数，
	 *                          给联合签名中客户端的函数bw_sm2_tps_client_hello
	 *                          当此参数为null时，由pirnd1len返回长度
	 * @int[]  [in/out] pirnd1len  length (32 bytes)
	 * @byte[] [out] rnd2                   输出随机数，给服务端bw_sm2_tps_server_response
	 *                          当此参数为null时，由pirnd2len返回长度
	 * @int[]  [in/out] pirnd2len  length (32 bytes)
	 * @byte[] [out] rnd3                   输出随机数，给服务端bw_sm2_tps_server_response
	 *                          当此参数为null时，由pirnd3len返回长度
	 * @int[]  [in/out] pirnd3len  length (32 bytes)
	 * return: 0 succeed, 
	 *         else failed,
	 * 注意：rnd2 和 rnd3 的顺序不能错。
	 */
	public native int hf_a_as_sm2_sign_rnd_split(byte[] rnd,int irndlen,
			byte[] rnd1,int[] pirnd1len,byte[] rnd2,int[] pirnd2len,
			byte[] rnd3,int[] pirnd3len);

	//51.hf_a_as_sm9_sign_rnd_split
	/*
	 * function name: hf_a_as_sm9_sign_rnd_split   
	 * description: 将标准的单人sm9签名过程中的输入随机数分解为联合签名的客户端和服务端的随机数
	 * params:	 	  
	 * @byte[] [in] rnd         输入随机数
	 * @int    [in] irndlen     length(32 bytes)
	 * @byte[] [out] rnd1                  输出随机数，
	 *                          给联合签名中客户端的函数bw_ibe_sm9_tps_sign_user_s1_m
	 *                          当此参数为null时，由pirnd1len返回长度
	 * @int[]  [in/out] pirnd1len  length (32 bytes)
	 * @byte[] [out] rnd2                   输出随机数，给服务端bw_ibe_sm9_tps_sign_server_s1_m
	 *                          当此参数为null时，由pirnd2len返回长度
	 * @int[]  [in/out] pirnd2len  length (32 bytes)
	 * @byte[] [out] rnd3                   输出随机数，给服务端bw_ibe_sm9_tps_sign_server_s1_m
	 *                          当此参数为null时，由pirnd3len返回长度
	 * @int[]  [in/out] pirnd3len  length (32 bytes)
	 * return: 0 succeed, 
	 *         else failed,
	 * 注意：rnd2 和 rnd3 的顺序不能错。
	 */
	public native int hf_a_as_sm9_sign_rnd_split(byte[] rnd,int irndlen,
			byte[] rnd1,int[] pirnd1len,byte[] rnd2,int[] pirnd2len,
			byte[] rnd3,int[] pirnd3len);
	
	//52.hf_a_as_sm9_signkey_split
	/*
	 * function name: hf_a_as_sm9_signkey_split   
	 * description: 将SM9标准的单人签名私钥分解为客户端和服务端的密钥
	 * params:	 	  
	 * @byte[] [in] ds         输入sm9签名私钥
	 * @int    [in] idslen     length(65 bytes)
	 * @byte[] [out] ds_user          输出客户端私钥,当此参数为null时，由pids_userlen返回长度
	 * @int[]  [in/out] pids_userlen  length (65 bytes)
	 * @byte[] [out] ds_server          输出服务端私钥,当此参数为null时，由pids_serverlen返回长度
	 * @int[]  [in/out] pids_serverlen  length (32 bytes)
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int hf_a_as_sm9_signkey_split(byte[] ds,int idslen,			
			byte[] ds_user,int[] pids_userlen,
			byte[] ds_server,int[] pids_serverlen);
	
	//53.hf_a_as_sm9_enckey_split
	/*
	 * function name: hf_a_as_sm9_enckey_split   
	 * description: 将SM9标准的单人解密私钥分解为客户端和服务端的密钥
	 * params:	 	  
	 * @byte[] [in] de         输入sm9解密私钥
	 * @int    [in] idelen     length(129 bytes)
	 * @byte[] [out] de_user          输出客户端私钥,当此参数为null时，由pide_userlen返回长度
	 * @int[]  [in/out] pide_userlen  length (32 bytes)
	 * @byte[] [out] de_server          输出服务端私钥,当此参数为null时，由pide_serverlen返回长度
	 * @int[]  [in/out] pide_serverlen  length (129 bytes)
	 * return: 0 succeed, 
	 *         else failed,
	 */
	public native int hf_a_as_sm9_enckey_split(byte[] de,int idelen,			
			byte[] de_user,int[] pide_userlen,
			byte[] de_server,int[] pide_serverlen);
	
	/*
	 * this is used to load the 'softalgjni' library on application startup.
	 */
	//static {
	//	System.loadLibrary("softalgjni");
	//}
}
