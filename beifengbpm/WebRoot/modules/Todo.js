Ext.Loader.setPath('Ext.us','/jscomponent/extjs/ux');
var store;
function optionflow(id,workitemId,processinsId){
	alert(id+" "+workitemid+" "+processinsId)
}
Ext.define('BeifengBPM.Todo',{
	extend:'Ext.ux.desktop.Module',
	requires:[],
	id:'todo',
	init:function(){
		this.launcher={
			text:'待办管理',
			iconCls:'notepad',
			handler:this.createWindow,
			scope:this
		}
	},
	createTodoGrid:function(){
		Ext.define('TodoModel',{
			extend:'Ext.data.Model',
			fields:[
			    'id',
			    'workitemId',
			    'modulename',
			    'workitemname',
			    'processinsId',
			    'todostate',
			    'createtime'
			]
		});
		var store = Ext.create('Ext.data.Store',{
			model:'TodoModel',
			pageSize:10,
			proxy:{
				type:'ajax',
				url:'/beifengbpm/querytodotableforuser.do',
				reader:{
					root:'todoinfos',
					totalProperty:'totalCount'
				}
			}
		});
		var grid=Ext.create('Ext.grid.Panel',{
			title:'待办管理',
			store:store,
			border:false,
			columnLines:true,
			disableSelection:false,
			columns:[{
				id:'id',
				dataIndex:'id',
				hidden:true
			},{
				id:'processinsId',
				dataIndex:'processinsId',
				hidden:true
			},{
				id:'workitemId',
				dataIndex:'workitemId',
				hidden:true
			},{
				id:'modulename',
				dataIndex:'modulename',
				text:'模块名称'
			},{
				id:'workitemname',
				dataIndex:'workitemname',
				text:'处理节点'
			},{
				id:'todostate',
				dataIndex:'todostate',
				hidden:true
			},{
				id:'createtime',
				dataIndex:'createtime',
				text:'待办接受时间',
				width:150
			},{
				text:'操作',
				renderer:function(value,metaData,record,rowIndex,colIndex,store,view){
					var rec = store.getAt(rowIndex);
					var s="<a href='/beifengbpm/todo.do?id="+rec.get('id')+"&workitemId="+rec.get('workitemId')+"&processinsId="+rec.get('processinsId')+"' target='_blank'>处理</a>"
					return s;
				}
			}],
			bbar:Ext.create('Ext.PagingToolbar',{
				store:store,
				displayInfo:true,
				displayMsg:'当前显示第 {0}条 至 {1}条记录 ，共 {2}条记录',
	            emptyMsg: "暂无可用数据"
			}),
			tbar:[{
				width:240,
				fieldLabel:'搜索',
				xtype:'searchfield',
				labelWidth:50,
				store:store
			},{
				width:80,
				text:'转办',
				xtype:'button',
				handler:function(){
					var record = grid.getSelectionModel().getSelection();
					if(record.length==0){
						Ext.Msg.alert('警告','请至少选择一条需要转办的流程');
						return;
					}
					var id=record[0].get('id');
					Ext.Ajax.request({
						url:'/beifengbpm/according.do',
						method:'POST',
						params:{
							id:id
						},
						success:function(response,options){
							if(response.responseText=='1'){
								Ext.Msg.alert('成功','流程转办成功');
								store.load();
							}
						}
					})
				}
			}]
		});
		store.loadPage(1);
		return grid;
	},
	createTodoGrid:function(){
		Ext.define('TodoModel',{
			extend:'Ext.data.Model',
			fields:[
				'id',
				'workitemId',
				'modulename',
				'workitemname',
				'processinsId',
				'todostate',
				'createtime'
			]
		});
		var store=Ext.create('Ext.data.Store',{
			model:'TodoModel',
			pageSize:10,
			proxy:{
				type:'ajax',
				url:'beifengbpm/querytodotableforuser.do',
				reader:{
					root:'/todoinfos',
					totalProperty:'totalCount'
				}
			}
		});
		var grid=Ext.create('Ext.grid.Panel',{
			title:'待办管理',
			store:store,
			border:false,
			columnLines:true,
			disableSelection:false,
			columns:[{
				id:'id',
				dataIndex:'id',
				hidden:true
			},{
				id:'processinsId',
				dataIndex:'processinsId',
				hidden:true
			},{
				id:'workitemId',
				dataIndex:'workitemId',
				hidden:true
			},{
				id:'modulename',
				dataIndex:'modulename',
				text:'模块名称'
			},{
				id:'workitemname',
				dataIndex:'workitemname',
				text:'处理节点'
			},{
				id:'todostate',
				dataIndex:'todostate',
				hidden:true
			},{
				id:'createtime',
				dataIndex:'createtime',
				text:'待办接收时间',
				width:150
			},{
				text:'操作',
				renderer:function(value,metaData,record,rowIndex,colIndex,store,view){
					var rec=store.getAt(rowIndex);
					var s="<a href='/beifengbpm/todo.do?id="+rec.get('id')+"&workitemId="+rec.get('workitemId')+"&processinsId="+rec.get('processinsId')+"' target='_blank'>处理</a>"
					return s;
				}
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
	        	width:80,
	        	text:'转办',
	        	xtype:'button',
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('警告','请选择一条需要转办的流程！');
	        			return;
	        		}
	        		var id=record[0].get('id');
	        		Ext.Ajax.request({
	        			url:'/beifengbpm/according.do',
	        			method:'POST',
	        			params:{
	        				id:id
	        			},
	        			success:function(response,options){
	        				if(response.responseText=='1'){
	        					Ext.Msg.alert('成功','流程转办成功！');
	        					store.load();
	        				}
	        			}
	        		})
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
	},	
	createHavetodoGrid:function(){
		Ext.define('HaveTodoModel',{
			extend:'Ext.data.Model',
			fields:[
				'havetodoId',
				'workitemId',
				'userId',
				'modulename',
				'workitemname',
				'processinsId',
				'todostate',
				'processtime'
			]
		});
		var store=Ext.create('Ext.data.Store',{
			model:'HaveTodoModel',
			pageSize:10,
			proxy:{
				type:'ajax',
				url:'/beifengbpm/queryhavetodolist.do',
				reader:{
					root:'havetodoinfos',
					totalProperty:'totalCount'
				}
			}
		});
		var grid=Ext.create('Ext.grid.Panel',{
			title:'已办管理',
			store:store,
			border:false,
			columnLines:true,
			disabelSelection:false,
			columns:[{
				id:'havetodoId',
				dataIndex:'havetodoId',
				hidden:true
			},{
				id:'processinsId',
				dataIndex:'processinsId',
				hidden:true
			},{
				id:'workitemId',
				dataIndex:'workitemId',
				hidden:true
			},{
				id:'modulename',
				dataIndex:'modulename',
				text:'模块名称'
			},{
				id:'workitemname',
				dataIndex:'workitemname',
				text:'处理节点'
			},{
				id:'todostate',
				dataIndex:'todostate',
				hidden:true
			},{
				id:'processtime',
				dataIndex:'processtime',
				text:'处理时间',
				width:150
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
	        	text:'取回',
	        	xtype:'button',
	        	width:80,
	        	handler:function(){
	        		var record=grid.getSelectionModel().getSelection();
	        		if(record.length==0){
	        			Ext.Msg.alert('警告','请至少选择一条需要取回的流程！');
	        			return;
	        		};
	        		var havetodoId=record[0].get('havetodoId');
	        		Ext.Ajax.request({
	        			url:'/beifengbpm/retrieve.do',
	        			method:'POST',
	        			params:{
	        				havetodoId:havetodoId
	        			},
	        			success:function(response,options){
	        				if(response.responseText=='retrieveok'){
	        					Ext.Msg.alert('成功','流程取回成功！');
	        					store.lead();
	        				}else{
	        					Ext.Msg.alert('失败','流程取回失败！');
	        				}
	        			}
	        		})
	        	}
	        }]
		});
		store.loadPage(1);
		return grid;
	},
	createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('todo');
        if(!win){
            win = desktop.createWindow({
                id: 'todo',
                title:'流程管理',
                width:600,
                height:400,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout: 'accordion',
                items: [this.createTodoGrid(),this.createHavetodoGrid()]
            });
        }
        win.show();
        return win;
    }
});