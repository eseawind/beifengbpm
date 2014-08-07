Ext.Loader.setPath('Ext.ux', '/jscomponent/extjs/ux/');
var store;
Ext.define('BeifengBPM.Leave', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
    ],

    id:'leave',

    init : function(){
        this.launcher = {
            text: '请假管理',
            iconCls:'notepad',
            handler : this.createWindow,
            scope: this
        }
    },
	
	createLeaveGrid:function(){
		Ext.define('LeaveModel',{
			extend:'Ext.data.Model',
			fields:[
				'leaveId',
				'leavename',
				'starttime',
				'endtime',
				'day',
				'createtime',
				'createname',
				'processinsId',
				'leavestate'
			]
		});
		var store=Ext.create('Ext.data.Store',{
			model:'LeaveModel',
			pageSize:10,
			proxy:{
				type:'ajax',
				url:'/beifengbpm/queryLeaveList.do',
				reader:{
					root:'leaveindos',
					totalProperty:'totalCount'
				}
			}
		});
		var grid=Ext.create('Ext.grid.Panel',{
			store:store,
			border: false,
			columnLines: true, 
			disableSelection: false,
			columns:[{
				id:'leaveId',
				dataIndex:'leaveId',
				hidden:true
			},{
				id:'processinsId',
				dataIndex:'processinsId',
				hidden:true
			},{
				id:'leavename',
				dataIndex:'leavename',
				text:'请假名称'
			},{
				id:'starttime',
				dataIndex:'starttime',
				text:'开始时间'
			},{
				id:'endtime',
				dataIndex:'endtime',
				text:'结束时间'
			},{
				id:'day',
				dataIndex:'day',
				text:'请假天数'
			},{
				id:'createtime',
				dataIndex:'createtime',
				text:'请假时间'
			},{
				id:'createname',
				dataIndex:'createname',
				text:'请假人'
			},{
				id:'leavestate',
				dataIndex:'leavestate',
				text:'流程状态'
			}],
			bbar: Ext.create('Ext.PagingToolbar', {
	            store: store,
	            displayInfo: true,
	            displayMsg: '当前显示第 {0}条 至 {1}条记录 ，共 {2}条记录',
	            emptyMsg: "暂无可用数据"
       		}),
       		tbar:[{
	        	width:240,
	        	fieldLabel:'搜素',
	        	xtype:'searchfield',
	        	labelWidth:50,
	        	store: store 
	        },{
	        	text:'请假申请',
	        	xtype:'button',
	        	width:80,
	        	handler:function(){
	        		var win;
	        		var form=Ext.create('Ext.form.Panel',{
	        			frame:true,
	        			defaultType:'textfield',
	        			items:[{
	        				id:'leavename',
	        				name:'leavename',
	        				fieldLabel:'请假事由'
	        			},{
	        				id:'starttime',
	        				name:'starttime',
	        				fieldLabel:'开始时间',
	        				xtype:'datefield',
	        				format: 'Y-m-d H:i:s'
	        			},{
	        				id:'endtime',
	        				name:'endtime',
	        				fieldLabel:'结束时间',
	        				xtype:'datefield',
	        				format: 'Y-m-d H:i:s'
	        			},{
	        				id:'day',
	        				name:'day',
	        				fieldLabel:'请假天数',
	        				xtype:'numberfield',
	        				step:0.5,
	        				minValue:0
	        			}],
	        			buttons:[{
	        				text:'重置',
	        				handler:function(){
	        					this.up('form').getForm().reset();
	        				}
	        			},{
	        				text:'提交',
	        				handler:function(){
	        					var f=this.up('form').getForm();
	        					var leavename=f.findField('leavename').getValue();
	        					var starttime=f.findField('starttime').getValue();
	        					var endtime=f.findField('endtime').getValue();
	        					var day=f.findField('day').getValue();
	        					Ext.Ajax.request({
	        						url:'/beifengbpm/startLeave.do',
	        						method:'POST',
	        						params:{
	        							leavename:leavename,
	        							starttime:starttime,
	        							endtime:endtime,
	        							day:day
	        						},
	        						success:function(response,options){
	        							if(response.responseText=='startok'){
	        								Ext.Msg.alert('成功','请假申请单已经成功提交，请等待审核！');
	        								win.close();
	        								store.load();
	        							}
	        						}
	        					});
	        				}
	        			}]	
	        		});
	        		win=Ext.create('Ext.window.Window',{
	        			width:300,
	        			height:240,
	        			title:'请假申请单',
	        			layout:'fit',
	        			items:[form]
	        		})
	        		win.show();
	        	}
	        },{
	        	text:'流程驳回',
	        	xtype:'button',
	        	width:80,
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('警告','请至少选择一条需要驳回的流程！');
	        			return;
	        		};
	        		var havetodowin;
	        		var processinsId=record[0].get('processinsId');
	        		Ext.define('HavetodoModel',{
						extend:'Ext.data.Model',
						fields:[
							'workitemId',
							'workitemname'
						]
					});
					var havetodostore=Ext.create('Ext.data.Store',{
						model:'HavetodoModel',
						pageSize:10,
						proxy:{
							type:'ajax',
							url:'/beifengbpm/findhavetodoList.do',
							reader:{
								root:'havetodoinfos',
								totalProperty:'totalCount'
							}
						},
						listeners:{
				        	beforeload:function(store,operation,eOpts){
				        		var newparams={processinsId:processinsId};
				        		Ext.apply(havetodostore.proxy.extraParams,newparams);
				        	}
				        }
					});
					var havetodogrid=Ext.create('Ext.grid.Panel',{
						store:havetodostore,
						border: false,
						columnLines: true, 
						disableSelection: false,
						columns:[{
							id:'workitemId',
							dataIndex:'workitemId',
							hidden:true
						},{
							id:'workitemname',
							text:'工作项',
							dataIndex:'workitemname'
						}],
						bbar: Ext.create('Ext.PagingToolbar', {
				            store: havetodostore,
				            displayInfo: true,
				            displayMsg: '当前显示第 {0}条 至 {1}条记录 ，共 {2}条记录',
				            emptyMsg: "暂无可用数据"
			       		}),
			       		tbar:[{
				        	width:80,
				        	xtype:'button',
				        	text:'驳回',
				        	handler:function(){
				        		var hrecord=havetodogrid.getSelectionModel().getSelection();
				        		if(hrecord.length==0){
				        			Ext.Msg.alert('警告','请至少选择一条需要驳回的流程！');
				        			return;
				        		};
				        		var workitemId=hrecord[0].get('workitemId')
				        		var userId="";
				        		Ext.Ajax.request({
				        			url:'/beifengbpm/torejected.do',
				        			method:'POST',
				        			params:{
				        				workitemId:workitemId
				        			},
				        			success:function(response,options){
				        				if(response.responseText=='4'){
				        					var havetodouserwin;
				        					Ext.define('HavetodoUserModel',{
												extend:'Ext.data.Model',
												fields:[
													'userId',
													'username'
												]
											});
											var havetodouserstore=Ext.create('Ext.data.Store',{
												model:'HavetodoUserModel',
												pageSize:10,
												proxy:{
													type:'ajax',
													url:'/beifengbpm/queryhavetouserList.do',
													reader:{
														root:'havetodousers',
														totalProperty:'totalCount'
													}
												},
												listeners:{
										        	beforeload:function(store,operation,eOpts){
										        		var newparams={processinsId:processinsId,workitemId:workitemId};
										        		Ext.apply(havetodouserstore.proxy.extraParams,newparams);
										        	}
										        }
											});
											var havetodousergrid=Ext.create('Ext.grid.Panel',{
												store:havetodouserstore,
												border: false,
												columnLines: true, 
												disableSelection: false,
												columns:[{
													id:'userId',
													dataIndex:'userId',
													hidden:true
												},{
													id:'username',
													text:'工作项',
													dataIndex:'username'
												}],
												bbar: Ext.create('Ext.PagingToolbar', {
										            store: havetodouserstore,
										            displayInfo: true,
										            displayMsg: '当前显示第 {0}条 至 {1}条记录 ，共 {2}条记录',
										            emptyMsg: "暂无可用数据"
									       		}),
									       		tbar:[{
										        	width:80,
										        	xtype:'button',
										        	text:'驳回',
										        	handler:function(){
										        		var hurecord=havetodousergrid.getSelectionModel().getSelection();
										        		if(hurecord.length==0){
										        			Ext.Msg.alert('警告','请至少选择一条需要驳回的流程！');
										        			return;
										        		};
										        		userId=hurecord[0].get('userId');
										        		Ext.Ajax.request({
							        						url:'/beifengbpm/rejected.do',
							        						method:'POST',
							        						params:{
							        							workitemId:workitemId,
							        							processinsId:processinsId,
							        							userId:userId
							        						},
							        						success:function(response,options){
							        							if(response.responseText=='ok'){
							        								Ext.Msg.alert('流程驳回成功！');
							        								havetodouserwin.close();
							        							}
							        						}
							        					});
										        	}
										        }]
											});
											havetodouserstore.loadPage(1);
				        					havetodouserwin=Ext.create('Ext.window.Window',{
												width:200,
												height:300,
												title:'处理人列表',
												layout:'fit',
												items:[havetodousergrid]
											})
											havetodowin.close();
											havetodouserwin.show();
				        				}else{
				        					Ext.Ajax.request({
				        						url:'/beifengbpm/rejected.do',
				        						method:'POST',
				        						params:{
				        							workitemId:workitemId,
				        							processinsId:processinsId,
				        							userId:userId
				        						},
				        						success:function(response,options){
				        							if(response.responseText=='ok'){
				        								Ext.Msg.alert('流程驳回成功！');
				        								havetodowin.close();
				        							}
				        						}
				        					});
				        				}
				        			}
				        		});
				        	} 
				        }]
					});
					havetodostore.loadPage(1);
					havetodowin=Ext.create('Ext.window.Window',{
						width:200,
						height:300,
						title:'驳回列表',
						layout:'fit',
						items:[havetodogrid]
					})
					havetodowin.show();
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
	},
	
    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('leave');
        if(!win){
            win = desktop.createWindow({
                id: 'leave',
                title:'请假管理',
                width:700,
                height:400,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout: 'fit',
                items: [this.createLeaveGrid()]
            });
        }
        win.show();
        return win;
    }
});

