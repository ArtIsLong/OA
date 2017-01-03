package com.gnd.oa.base;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("unchecked")
public abstract class ModelDrivenBaseAction<T> extends BaseAction implements
		ModelDriven<T> {
	private static final long serialVersionUID = 1L;
	private static transient Log log = LogFactory
			.getLog(ModelDrivenBaseAction.class);
	protected T model;

	public ModelDrivenBaseAction() {
		try {
			// 通过反射获取model的真实类型
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
			// 通过反射创建model的实例
			model = clazz.newInstance();
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new RuntimeException(e);
		}
	}

	public T getModel() {
		return model;
	}

}
