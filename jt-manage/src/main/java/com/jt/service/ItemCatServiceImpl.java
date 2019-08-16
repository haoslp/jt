package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	//返回的是商品分类名称
	@Override
	public String findItemCatNameById(Long itemCatId) {
		
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		
		return itemCat.getName();
	}

	/**
	 * 1.根据parentId查询ItemCatList
	 * 2.循环遍历/itemCat~~~~EasyUITree
	 */
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = 
				new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		//根据父级查询子级信息
		List<ItemCat> itemCatList = 
				itemCatMapper.selectList(queryWrapper);
		//将数据进行转化
		List<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCat : itemCatList) {
			Long id = itemCat.getId();
			String text = itemCat.getName();
			String state = 
					itemCat.getIsParent() ? "closed" : "open";
			EasyUITree tree = 
					new EasyUITree(id, text, state);
			treeList.add(tree);
		}
		
		return treeList;
	}
	
	
	
}
