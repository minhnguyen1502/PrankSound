package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.dialog.exit;

import android.content.Context;

import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.base.BaseDialog;
import funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.databinding.DialogExitAppBinding;

public class ExitAppDialog extends BaseDialog<DialogExitAppBinding> {
    IClickDialogExit iBaseListener;
    Context context;

    public ExitAppDialog(Context context, Boolean cancelAble) {
        super(context, cancelAble);
        this.context = context;
    }


    @Override
    protected DialogExitAppBinding setBinding() {
        return DialogExitAppBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void bindView() {
        binding.btnCancelQuitApp.setOnClickListener(view -> iBaseListener.cancel());

        binding.btnQuitApp.setOnClickListener(view -> iBaseListener.quit());

    }

    public void init(IClickDialogExit iBaseListener) {
        this.iBaseListener = iBaseListener;
    }

}
