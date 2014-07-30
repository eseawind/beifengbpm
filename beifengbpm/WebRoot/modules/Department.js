Ext.Loader.setPath('Ext.ux', '/jscomponent/extjs/ux');
var store;
var userstore;
var droptarget;
Ext.define('BeifengBPM.Department', {
	extend : 'Ext.ux.desktop.Module',

	requires : [
	// 'Ext.data.TreeStore',
	// 'Ext.Tree.Panel'
	],
	
	id:'department',
	
	init:function(){
		this.launcher={
				text:'组织机构管理',
				iconCls:"notepad",
				handler:this.createWindow,
				scope:this
		}
	},
	
	createTreePanel:function(){
		store=Ext.create('Ext.data.TressStore',{
			proxy:{
				type:'ajax',
				url:'/beifengbpm/queryDeparttree.do'
			},
			sorters:[{
				property:'leaf',
				direction:'ASC'
			},{
				property:'text',
				direction:'ASC'
			}]
		});
		var treepanel = Ext.create('Ext.tree.Panel',{
			store:store,
			rootVisible:false,
			uerArrows:true,
			frame:true,
			title:'部門樹',
			width:250,
			height:350,
			vieConfig:{
				listeners:{
					render:function(tres){
						var dropTarget = new Ext.dd.DropTarget(tree.el,{
							ddGroup:'gridtotree',
							copy:false,
							notifyDrop:function(dragSource,event,data){
								var id="";
								for(var i=0;i<data.records.length;i++){
									id+="'"+data.records[i].data.flowuserId+"'";
									if(i<data.records.length-1){
										id+=",";
									}
								}
								Ext.Ajax.request({
									url:'/beifengbpm/updataUsersDepart.do',
									method:'POST',
									params:{
										id:id,
										droptarget:droptarget
									},
									success:function(reponse,options){
										if(reponse.responseText=='updataok'){
											userstore.load();
										}else{
											Ext.Msg.alert('錯誤','抱歉，更新用戶所屬部門失敗');
										}
									},
									failure:function(response,options){
										Ext.Msg.alert('錯誤','服務器異常，請稍後再試');
									}
								});
								return true;
							}
						});
					}
				}
			},
			listeners:{
				itemouseenter:function(view,record,item,index,e,eOpts){
					var parentid=record.data.id;
					document.getElementById('departmentId').value=parentid;
					userstore.load();
				},
				itemcontextmenu:function(view,record,item,index,e,eOpts){
					e.preverntDefault();
					e.stopEvent;
					//創建菜單
					var node = Ext.create('Ext.menu.Menu',{
						items:[{
							text:'添加部門',
							handler:function(){
								var parentid = record.data.id;
								Ext.Msg.prompt('添加部門','請輸入部門名稱:',function(bt,txt){
									if(bt=='ok'){
										if(txt==''){
											Ext.Msg.alert('錯誤','請輸入部門名稱');
										}else{
											Ext.Ajax.request({
												url:'/beifengbpm/adddepartment.do',
												method:'POST',
												params:{
													departmentid:txt,
													parentId:parentid
												},
												success:function(response,options){
													var result = response.responseText;
													if(result=='addok'){
														Ext.Msg.alert('恭喜','添加部門成功！');
														store.load();
													}else{
														Ext.Msg.alert('抱歉','添加失敗，請稍後再試');
													}
												},
												failure:function(reponse,options){
													Ext.Msg.alert('抱歉','服務器錯誤，請稍後再試');
												}
											});
										}
									}
								});
							}
						},{
							text:'修改部门',
							handler:function(){
								var parentid = record.data.id;
								var text = record.data.text;
								Ext.Msg.prompt('修改部门','请输入部门名称',function(bt,txt){
									if(bn=='ok'){
										if(txt!=text){
											Ext.Msg.alert('错误','请输入部门名称');
										}else{
											Ext.Ajax.request({
												url:'/beifengbpm/updatadepartment.do',
												methos:'POST',
												params:{
													dapartmentname:txt,
													parentId:parentid
												},
												success:function(response,options){
													var result=response.responseText;
													if(result=='updateok'){
													Ext.Msg.alert('成功','修改部门成功');
													store.load();
												}else{
													Ext.Msg.alert('抱歉','修改失败，请稍后再试！');
												}
												},
												failure:function(response,options){
													Ext.Msg.alert('错误','服务器错误，请稍后再试');
												}
											});
										}
									}
								
								});
							}
						},{
							text:'删除部门',
							handler:function(){
								var parentid = record.data.id;
								Ext.Msg.confirm('刪除部門','您是否確認要刪除部門？',function(bt){
									if(bt=='yes'){
										Ext.Ajax.request({
											url:'/beifengbpm/deleteDepartment.do',
											method:'POST',
											params:{
												id:parentid
											},
											success:function(response,options){
												var result = response.responseText;
												if(result=='deleteok'){
													store.load();
												}else if(result=='deletemore'){
													Ext.Msg.alert('抱歉','當前部門有子部門，無法刪除');
												}else{
													Ext.Msg.alert('抱歉','服務器錯誤，請稍後再試');
												}
											},
											failure:function(response,options){
												Ext.Msg.alert('抱歉','服务器错误，请稍后添加！');
											}
										})
									}
								});
							}
						},{
							xtype:'menuseparator'
						},{
							text:'添加用戶',
							handler:function(){
								var parentid = record.data.id;
								var win;
								var form = Ext.create('Ext.form.Panel',{
									defaultType:'textfield',
									items:[{
										id:'flowusername',
										name:'flowusername',
										fieldLabel:'用戶名',
										allowBlank:false
									},{
										id:'flowloginname',
										name:'flowloginname',
										fieldLabel:'登錄名',
										allowBlank:false
									}],
									button:[{
										text:'重置',
										handler:function(){
											this.up('form').getForm().reset();
										}
									},{
										text:'添加',
										handler:function(){
											var  f= this.up('form').getForm();
											var flowusername = f.findField('flowusername').getValue();
											var flowloginname = f.findField('flowloginname').getValue();
											Ext.Ajax.request({
												url:'/beifengbpm/addflowuser.do',
												method:'POST',
												params:{
													departmentId:parentid,
													flowusername:flowusername,
													flowloginname:flowloginname
												},
												success:function(response,options){
													if(response.responseText=='addok'){
														Ext.Msg.alert('恭喜','添加用戶成功');
														win.close();
														document.getElementById('departmentId').value=parentid;
														userstore.load();
													}
												}
											});
										}
									}],
								});
								win=Ext.create('Ext.window.Window',{
									title:'添加用戶',
									width:300,
									height:300,
									layout:'fit',
									items:[form],
									modal:true
								});
								win.show();
							}
						}]
					})
					//讓菜單展現
					node.showAt(e.getXY());
				}
			}	
		});
		return treepanel;
	},
	createUserPanel:function(){
		Ext.define('UserModel',{
			extend:'Ext.data.Model',
			fields:[
			        'flowuserId',
			        'flowusername',
			        'flowloginname',
			        'departmentname',
			        'flowroleId',
			        'flowrolename'
			        ]
		});
		userstore=Ext.create('Ext.data.Store',{
			model:'UserModel',
			pageSize:5,
			//是否在服務端排序
			remoteSort:true,
			proxy:{
				type:'ajax',
				url:'/beifengbpm/queryUserListByDepartment.do',
				reader:{
					root:'users',
					totalProperty:'totalCount'
				}
			},
			listeners:{
				beforeload:function(store,operation,eOpts){
					var departmentId= document.getElementById('departmentId').value;
					var newparams = {departmentId:departmentId};
					Ext.apply(userstore.proxy.extraParams,newparams);
				}
			}
		});
		//定義修改組件
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing',{
			clicksToMoveEditor:1,
			autoCancle:false,
			listeners:{
				edit:function(editor,e,eOpts){
					var newValues = editor.newValues;
					var originalValues = editor.originalValues;
					if(newValues.flowusername!=originalValues.flowusername || newValues.flowloginname!=originalValues.flowloginname || newValues.flowrolename!=originalValues.flowrolename){
	        			Ext.Ajax.request({
	        				url:'/beifengbpm/updateuser.do',
	        				method:'POST',
	        				params:{
	        					flowuserId:newValues.flowuserId,
	        					flowusername:newValues.flowusername,
	        					flowloginname:newValues.flowloginname,
	        					flowrolename:newValues.flowrolename
	        					
	        				},
	        				success:function(response,options){
	        					var result = response.responseText;
	        					if(result=='updataok'){
	        						Ext.Msg.alert('成功','修改用戶成功');
	        						userstore.load();
	        					}else{
	        						Ext.Msg.alert('抱歉','服務器錯誤，請稍後再試');
	        					}
	        				},
	        				failure:function(response,options){
								Ext.Msg.alert('抱歉','服务器错误，请稍后添加！');
							}
	        			})
					}
				}
			}
		});
		
		var selModel = Ext.create('Ext.selection.CheckboxModel');
		var grid = Ext.create('Ext.grid.Panel',{
			width:540,
			height:350,
			store:userstore,
			selModel:selModel,
			border:false,
			columnLines:true,
			disableSelection:false,
			plugins:[rowEditing],
			viewConfig:{
				plugins:[
				         Ext.create('Ext.grid.plugin.DragDrop',{
				        	 ddGroup:'gridtotree',
				        	 enableDrop:true
				         })
				         ]
			},
			columns:[{
				name:'flowuserId',
				dataIndex:'flowuserId',
				flex:1,
				hidden:true
			},{
				text:'用户名',
				anchor:'30%',
				sortable:false,
				dataIndex:'flowusername',
				editor:{
					allowBlank:false
				}
			},{
				text:'登录名',
				anchor:'30%',
				sortable:false,
				editor:{
					allowBlank:false
				}
			},{
				text:'部门',
				anchor:'30%',
				sortable:false,
				dataIndex:'departmentname'
			},{
				text:'flowroleId',
				dataIndex:'flowroleId',
				hidden:true
			},{
				text:'角色',
				anchor:'30%',
				sortable:false,
				dataIndex:'flowrolename',
				editor:{
					xtype:'combobox',
					queryMode:'local',
					displayField:'flowrolename',
					valueField:'flowrolename',
					emptyText:'请选择',
					store:new Ext.data.Store({
						singleton:true,
						proxy:{
							type:'ajax',
							url:'/beifengbpm/queryAllRole.do',
							actionMethods:'post',
							reader:{
								root:'roles',
								totalProperty:'totalCount'
							}
						},
						fields:['flowroleId','flowrolename'],
						autoLoad:true
					})
				}
			}],
			bbar:Ext.create('Ext.PagingToolbar',{
				store:userstore,
				displayInfo:true,
				displayMsg:'当前显示第{0}条至第{1}条记录，共{2}条记录',
				emptyMsg:"暂无可用数据"
			}),
			tbar:[{
				width:300,
				fieldLabel:'搜索',
				xtype:'searchfield',
				labelWidth:50,
				store:userstore
			},{
				text:'删除',
				xtype:'button',
				width:50,
				handler:function(){
					var record = grid.getSelectionModel().getSelection();
					if(record.length==0){
						Ext.Msg.alert('选择','请选择要删除的数据');
					}else{
						var ids ="";
						for(var i = 0;i<record.length;i++){
							ids+="'"+record[i].get('flowuserId')+"'";
							if(i<record.length-1){
								ids+=",";
							}
						}
						Ext.Ajax.request({
							url:'/beifengbpm/deleteusers.do',
							method:'POST',
							params:{
								ids:ids
							},
							success:function(response,options){
								var result=response.responseText;
								if(result=='deleteok'){
									Ext.Msg.alert('成功','删除用户成功！');
									userstore.load();
								}else{
									Ext.Msg.alert('抱歉','服务器错误，请稍后删除！');
								}
							},
							failure:function(response,options){
								Ext.Msg.alert('抱歉','服务器错误，请稍后删除！');
							}
						})
					}
				}
			},{
				text:'角色置空',
	        	xtype:'button',
	        	width:80,
	        	handler:function(){
	        		var record = grid.getSelectionModel().getSelection();
	        		if(record.length==o){
	        			Ext.Msg.alert('选择','请选择需要置空角色的用户！');
	        		}else{
	        			var ids="";
	        			for(var i=0;i<record.length;i++){
	        				ids+="'"+record[i].get('flowuserId')+"'";
	        				if(i<record.length-1){
	        					ids+=",";
	        				}
	        			}
	        			Ext.Ajax.request({
	        				url:'/beifengbpm/resetRole.do',
	        				method:'POST',
	        				params:{
	        					ids:ids
	        				},
	        				success:function(response,options){
	        					if(response.responseText=='updateok'){
	        						userstore.load();
	        					}else{
	        						Ext.Msg.alert('抱歉','重置失败，请稍后再试！');
	        					}
	        				},
	        				failure:function(response,options){
								Ext.Msg.alert('抱歉','服务器错误，请稍后再试！');
							}
	        			})
	        		}
	        	}
			},{
				text:'分配角色',
	        	xtype:'button',
	        	width:80,
	        	handler:function(){
	        		var rolewin;
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('选择','请选择需要分配角色的用户！');
	        		}else{
	        			var ids="";
	        			for(var i=0;i<record.length;i++){
	        				ids+="'"+record[i].get('flowuserId')+"'";
	        				if(i<record.length-1){
	        					ids+=",";
	        				}
	        			}
	        			Ext.define('RoleModel',{
							extend:'Ext.data.Model',
							fields:[
								'flowroleId',
								'flowrolename',
								'flowroleremark'
							]
						});
	        			var rolestore=Ext.create('Ext.data.Store',{
							model:'RoleModel',
							pageSize:10,
							proxy:{
								type:'ajax',
								url:'/beifengbpm/queryRoleList.do',
								reader:{
									root:'roles',
									totalProperty:'totalCount'
								}
							}
						});
	        			var rolegrid=Ext.create('Ext.grid.Panel',{
							store:rolestore,
							border: false,
							columnLines: true, 
							disableSelection: false,
							columns:[{
								id:'flowroleId',
								dataIndex:'flowroleId',
								hidden:true
							},{
								name:'flowrolename',
								dataIndex:'flowrolename',
								text:'角色名称'
							},{
								name:'flowroleremark',
								dataIndex:'flowroleremark',
								text:'角色描述'
							}],
							bbar: Ext.create('Ext.PagingToolbar', {
					            store: userstore,
					            displayInfo: true,
					            displayMsg: '当前显示第 {0}条 至 {1}条记录 ，共 {2}条记录',
					            emptyMsg: "暂无可用数据"
				       		}),
				       		tbar:[{
					        	width:240,
					        	fieldLabel:'搜素',
					        	xtype:'searchfield',
					        	labelWidth:50,
					        	store: rolestore 
					        },{
					        	xtype:'button',
					        	text:'确定分配',
					        	width:80,
					        	handler:function(){
					        		var records=rolegrid.getSelectionModel().getSelection();
					        		if(records.length==0){
					        			Ext.Msg.alert('警告','请选择一个需要分配用户的角色！');
					        			return;
					        		}
					        		var flowroleId=records[0].get('flowroleId');
					        		Ext.Ajax.request({
					        			url:'/beifengbpm/updateuserRole.do',
					        			method:'POST',
				                    	params:{
				                    		ids:ids,
				                    		flowroleId:flowroleId
				                    	},
				                    	success:function(response,options){
				                    		if(response.responseText=='updateok'){
				                    			Ext.Msg.alert('成功','分配角色成功！');
				                    			userstore.load();
				                    			rolewin.close();
				                    		}else{
				                    			Ext.Msg.alert('失败','分配角色失败！');
				                    		}
				                    	},
				                    	failure:function(response,options){
				                    		Ext.Msg.alert('错误','服务器异常，请稍后分配！');
				                    	}
					        		});
					        	}
					        }]
					    });
					    rolestore.loadPage(1);
					    rolewin=Ext.create('Ext.window.Window',{
							width:400,
							height:400,
							title:'选择角色',
							layout:'fit',
							items:[rolegrid]
						});
						rolewin.show();
	        		}
	        	}
	        }]
		});
		userstore.loadPage(1);
		return grid;
	},
    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('department');
        if(!win){
            win = desktop.createWindow({
                id: 'department',
                title:'部门管理',
                width:800,
                height:400,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout: {
			        type: 'table',
			        columns: 2
			    },
                items: [{
                    items:[this.createTreePanel()]
                },{
                	items:[this.createUserPanel()]
                }]
            });
        }
        win.show();
        return win;
    }
});
