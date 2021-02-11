package com.crystal.aplayer.module_base.base.moduleinitpack;

import androidx.annotation.Nullable;

import com.crystal.aplayer.module_base.base.BaseApplication;
import com.crystal.aplayer.module_base.base.moduleinitpack.contract.ModuleInitContract;

/**
 * 创建者 kiylx
 * 创建时间 2020/9/17 16:05
 * packageName：com.crystal.aplayer.common
 * 描述：
 */
public final class ModuleInitManager {
    private static volatile ModuleInitManager moduleInitManager = null;


    public static ModuleInitManager getInstance() {
        return moduleInitManager == null ? new ModuleInitManager() : moduleInitManager;
    }

    /**
     * @param application
     * @throws ClassNotFoundException
     */
    public void initModule(@Nullable BaseApplication application) throws ClassNotFoundException {
        for (String moduleName : AllInitConfig.CONFIG) {
            try {
                Class<?> clazz = Class.forName(moduleName);
                ModuleInitContract initContract= (ModuleInitContract) clazz.newInstance();
                // 调用初始化方法
                initContract.beforeInit(application);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param application
     * @throws ClassNotFoundException
     */
    public void initModuleAfter(@Nullable BaseApplication application) throws ClassNotFoundException {
        for (String moduleName : AllInitConfig.CONFIG) {
            try {
                Class<?> clazz = Class.forName(moduleName);
                ModuleInitContract initContract= (ModuleInitContract) clazz.newInstance();
                // 调用初始化方法
                initContract.afterInit(application);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
